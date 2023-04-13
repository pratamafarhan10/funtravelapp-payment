package com.funtravelapp.payment.service.transaction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funtravelapp.payment.kafka.dto.UpdateNotifStatusDTO;
import com.funtravelapp.payment.kafka.dto.UpdateOrderStatusDTO;
import com.funtravelapp.payment.kafka.dto.UpdateStatusPaymentDTO;
import com.funtravelapp.payment.model.account.Account;
import com.funtravelapp.payment.model.transaction.Transaction;
import com.funtravelapp.payment.repository.account.AccountRepository;
import com.funtravelapp.payment.repository.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository repository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    public void create(String data) {
        UpdateStatusPaymentDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            msg =  mapper.readValue(data, UpdateStatusPaymentDTO.class);

            Optional<Account> optAcc = accountRepository.findByUserId(msg.getUserId());
            Account acc = optAcc.orElseThrow();

            Transaction trx = new Transaction(msg.getUserId(), msg.getOrderId(), acc.getId(), msg.getAmount(), TransactionStatusEnum.PENDING.toString(), LocalDate.now(), "N");

            repository.save(trx);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Transaction updateStatus(int id, TransactionStatusEnum status) throws Exception {
        // Update status in db
        Transaction res = repository.updateStatus(id, status.toString());
        if (res == null) {
            throw new Exception();
        }

        // Produce kafka to order service
        ObjectMapper mapper = new ObjectMapper();
        UpdateOrderStatusDTO kafkaMessage = new UpdateOrderStatusDTO(res.getOrderId(), res.getStatus());
        String msgJson = mapper.writeValueAsString(kafkaMessage);
        kafkaTemplate.send("UpdateStatusOrder", msgJson);

        return res;
    }

    public void updateInvoiceStatus(String data) {
        UpdateNotifStatusDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            msg =  mapper.readValue(data, UpdateNotifStatusDTO.class);

            int res = repository.updateInvoiceStatus(msg.getOrderId(), msg.getStatus());
            if (res == 0){
                throw new Exception("Order id not found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public Transaction getById(int id) {
        Optional<Transaction> opt = repository.findById(id);
        return opt.orElseThrow();
    }
}
