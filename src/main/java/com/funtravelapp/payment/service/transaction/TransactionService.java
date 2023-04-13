package com.funtravelapp.payment.service.transaction;

import com.funtravelapp.payment.model.transaction.Transaction;
import com.funtravelapp.payment.repository.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository repository;

    public Transaction create(Transaction transaction){
        transaction.setStatus(TransactionStatusEnum.PENDING.toString());
        transaction.setIsInvoiceSent("N");
        transaction.setDate(LocalDate.now());

        return repository.save(transaction);
    }

    public Transaction updateStatus(int id, String status){
        return repository.updateStatus(id, status);
    }

    public boolean updateInvoiceStatus(int id, String status){
        int res = repository.updateInvoiceStatus(id, status);
        return res == 1;
    }

    public List<Transaction> getAll(){
        return repository.findAll();
    }

    public Transaction getById(int id){
        Optional<Transaction> opt = repository.findById(id);
        return opt.orElse(null);
    }
}
