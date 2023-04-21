package com.funtravelapp.payment.service.account;

import com.funtravelapp.payment.dto.account.CreateAccountRequest;
import com.funtravelapp.payment.dto.account.CreateAccountValidator;
import com.funtravelapp.payment.dto.account.TopUpBalanceRequest;
import com.funtravelapp.payment.ext.token.GetTokenAPI;
import com.funtravelapp.payment.ext.token.dto.GetTokenResponse;
import com.funtravelapp.payment.model.account.Account;
import com.funtravelapp.payment.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;
    @Autowired
    CreateAccountValidator createAccountValidator;
    @Autowired
    GetTokenAPI getTokenAPI;

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
        // Check token, get the user by token
        return repository.findByUserId(1);
    }

    public Account getById(String accountNumber){
        Optional<Account> opt = repository.findByNumber(accountNumber);

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

    public Account topUpBalance(String authorizationHeader, TopUpBalanceRequest request) throws Exception {
        if (request.getBalance().compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("Balance harus lebih dari 0");
        }

        GetTokenResponse user = getTokenAPI.getToken(authorizationHeader);

        Optional<Account> optionalAccount = repository.findByNumber(request.getAccountNumber());

        if (optionalAccount.isEmpty()){
            throw new Exception("Account tidak ditemukan");
        }

        Account acc = optionalAccount.get();

        // User id diganti oleh header
        if(!acc.getUserId().equals(user.getData().getId())){
            throw new Exception("Account bukan milik user");
        }

        acc.setBalance(acc.getBalance().add(request.getBalance()));

        repository.save(acc);

        return acc;
    }
}
