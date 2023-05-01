package com.funtravelapp.payment.transaction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funtravelapp.payment.transaction.dto.PaymentRequest;
import com.funtravelapp.payment.kafka.dto.CreateNotifDTO;
import com.funtravelapp.payment.kafka.dto.UpdateNotifStatusDTO;
import com.funtravelapp.payment.kafka.dto.UpdateOrderStatusDTO;
import com.funtravelapp.payment.kafka.dto.CreatePaymentDTO;
import com.funtravelapp.payment.account.model.Account;
import com.funtravelapp.payment.transaction.dto.SendEmailResponse;
import com.funtravelapp.payment.transaction.model.Transaction;
import com.funtravelapp.payment.user.User;
import com.funtravelapp.payment.account.repository.AccountRepository;
import com.funtravelapp.payment.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository repository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public DecimalFormat decimalFormat(){
        return new DecimalFormat("#,###.##");
    }

    public void create(String data) {
        CreatePaymentDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            msg = mapper.readValue(data, CreatePaymentDTO.class);

            Transaction trx = Transaction.builder()
                    .customerId(msg.getCustomerId())
                    .sellerId(msg.getSellerId())
                    .chainingId(msg.getChainingId())
                    .amount(msg.getAmount())
                    .status(TransactionStatusEnum.PENDING.toString())
                    .date(LocalDate.now())
                    .isInvoiceSent("N")
                    .build();

            repository.save(trx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Transaction payment(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId, PaymentRequest request) throws Exception {
        // Check if chaining id exist
        Optional<Transaction> optTrx = repository.findByChainingId(chainingId);
        if (optTrx.isEmpty()) {
            throw new Exception("Transaction not found");
        }
        // Check if transaction belong to the user
        Transaction trx = optTrx.get();
        if (!trx.getCustomerId().equals(user.getId())) {
            throw new Exception("Unauthorized");
        }

        if (trx.getStatus().equalsIgnoreCase(TransactionStatusEnum.SUCCESS.toString())){
            throw new Exception("Transaction already success");
        }

        // Find customer account
        Optional<Account> optCustAcc = accountRepository.findByNumber(request.getCustomerAcc());
        if (optCustAcc.isEmpty()) {
            throw new Exception("Customer account not found");
        }
        Account custAcc = optCustAcc.get();

        // Find seller account
        List<Account> optSellerAcc = accountRepository.findByUserId(trx.getSellerId());
        if (optSellerAcc.isEmpty()) {
            throw new Exception("Transaction failed, seller doesn't have any active account");
        }
        Account sellerAcc = optSellerAcc.get(0);

        // Debit credit balance
        if (custAcc.getBalance().compareTo(trx.getAmount()) < 0) {
            throw new Exception("Insufficient customer account balance, remaining balance: Rp. " + this.decimalFormat().format(custAcc.getBalance()));
        }
        custAcc.setBalance(custAcc.getBalance().subtract(trx.getAmount()));
        sellerAcc.setBalance(sellerAcc.getBalance().add(trx.getAmount()));

        accountRepository.save(custAcc);
        accountRepository.save(sellerAcc);

        // Set transaction data
        trx.setSellerAcc(sellerAcc.getNumber());
        trx.setCustomerAcc(request.getCustomerAcc());
        trx.setStatus(TransactionStatusEnum.SUCCESS.toString());
        repository.save(trx);

        // Produce kafka to order service
        ObjectMapper mapper = new ObjectMapper();
        String msgJson;
        try {
            UpdateOrderStatusDTO updateOrderStatusMsg = UpdateOrderStatusDTO.builder()
                    .chainingId(chainingId)
                    .status(TransactionStatusEnum.WAITING_FOR_CONFIRMATION.toString())
                    .customerAcc(custAcc.getNumber())
                    .sellerAcc(sellerAcc.getNumber())
                    .build();
            msgJson = mapper.writeValueAsString(updateOrderStatusMsg);
            kafkaTemplate.send("UpdateStatusOrder", msgJson);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to update order status");
        }

        try {
            // Produce kafka to notif service
            CreateNotifDTO createNotifMsg = CreateNotifDTO.builder()
                    .chainingId(chainingId)
                    .customerId(trx.getCustomerId())
                    .sellerId(trx.getSellerId())
                    .build();
            msgJson = mapper.writeValueAsString(createNotifMsg);
            kafkaTemplate.send("CreateNotif", msgJson);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to create email notification");
        }

        return trx;
    }

    @Transactional
    public void updateNotifStatus(String data) {
        UpdateNotifStatusDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            msg = mapper.readValue(data, UpdateNotifStatusDTO.class);

            int res = repository.updateInvoiceStatus(msg.getChainingId(), msg.getIsInvoiceSent());
            if (res == 0) {
                throw new Exception("Order id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllByUserId(String authorizationHeader, Map<String, Boolean> roles, User user) throws Exception {
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            return repository.findByCustomerId(user.getId());
        } else if (user.getRole().equalsIgnoreCase("SELLER")) {
            return repository.findBySellerId(user.getId());
        }
        throw new Exception("Unauthorized");
    }

    public Transaction getById(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) throws Exception {
        Optional<Transaction> opt = repository.findByChainingId(chainingId);
        if (opt.isEmpty()) {
            throw new Exception("Transaction not found");
        }
        Transaction trx = opt.get();

        if (trx.getSellerId().equals(user.getId()) || trx.getCustomerId().equals(user.getId())) {
            return trx;
        }

        throw new Exception("Unauthorized");
    }

    public SendEmailResponse retrySendEmail(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) throws Exception {
        Optional<Transaction> opt = repository.findByChainingId(chainingId);
        if (opt.isEmpty()) {
            throw new Exception("Transaction not found");
        }
        Transaction trx = opt.get();

        if (trx.getSellerId().equals(user.getId()) || trx.getCustomerId().equals(user.getId())) {
            if (trx.getIsInvoiceSent().equalsIgnoreCase("Y")) {
                throw new Exception("Transaction notification already sent");
            }
            // Produce kafka to notif service
            try {
                ObjectMapper mapper = new ObjectMapper();

                CreateNotifDTO createNotifMsg = CreateNotifDTO.builder()
                        .chainingId(chainingId)
                        .customerId(trx.getCustomerId())
                        .sellerId(trx.getSellerId())
                        .build();
                String msgJson = mapper.writeValueAsString(createNotifMsg);
                kafkaTemplate.send("CreateNotif", msgJson);

                return SendEmailResponse.builder()
                        .isResendSucceed(true)
                        .build();
            } catch (Exception e) {
                return SendEmailResponse.builder()
                        .isResendSucceed(false)
                        .build();
            }

        }

        throw new Exception("Unauthorized");
    }
}
