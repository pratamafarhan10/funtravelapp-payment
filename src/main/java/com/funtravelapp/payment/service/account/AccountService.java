package com.funtravelapp.payment.service.account;

import com.funtravelapp.payment.dto.account.CreateAccountRequest;
import com.funtravelapp.payment.dto.account.CreateAccountValidator;
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
    @Autowired
    CreateAccountValidator createAccountValidator;

    public Account create(CreateAccountRequest request) throws Exception {
        // Validation
        boolean isValid = createAccountValidator.setRequest(request).validate();
        if (!isValid){
            throw new Exception("Request tidak lolos validasi");
        }

        Account acc = Account.builder()
                .userId(request.getUserId())
                .bank(request.getBank())
                .number(request.getNumber())
                .name(request.getName())
                .type(request.getType())
                .build();

        return repository.save(acc);
    }

    public List<Account> getAll(){
        return repository.findAll();
    }

    public Account getById(int id){
        Optional<Account> opt = repository.findById(id);

        return opt.orElseThrow();
    }

    public Account update(int id, CreateAccountRequest request) throws Exception {
        // Validation
        boolean isValid = createAccountValidator.setRequest(request).validate();
        if (!isValid){
            throw new Exception("Request tidak lolos validasi");
        }

        boolean idExist = repository.existsById(id);
        if (!idExist){
            throw new Exception("Id tidak ditemukan");
        }

        Account acc = Account.builder()
                .id(id)
                .userId(request.getUserId())
                .bank(request.getBank())
                .number(request.getNumber())
                .name(request.getName())
                .type(request.getType())
                .build();

        return repository.save(acc);
    }

    public void delete(int id) throws Exception {
        boolean idExist = repository.existsById(id);
        if (!idExist){
            throw new Exception("Id tidak ditemukan");
        }
        repository.deleteById(id);
    }

}
