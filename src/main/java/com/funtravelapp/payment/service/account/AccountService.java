package com.funtravelapp.payment.service.account;

import com.funtravelapp.payment.model.account.Account;
import com.funtravelapp.payment.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;

    public Account create(Account account){
        return repository.save(account);
    }

    public List<Account> getAll(){
        return repository.findAll();
    }

    public Account getById(int id){
        Optional<Account> opt = repository.findById(id);

        return opt.orElse(null);
    }

}
