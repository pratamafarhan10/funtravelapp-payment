package com.funtravelapp.payment.account.service;

import com.funtravelapp.payment.account.dto.CreateAccountRequest;
import com.funtravelapp.payment.account.validator.CreateAccountValidator;
import com.funtravelapp.payment.account.dto.TopUpBalanceRequest;
import com.funtravelapp.payment.ext.token.GetTokenAPI;
import com.funtravelapp.payment.account.model.Account;
import com.funtravelapp.payment.user.User;
import com.funtravelapp.payment.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;
    @Autowired
    CreateAccountValidator createAccountValidator;
    @Autowired
    GetTokenAPI getTokenAPI;

    public Account create(String authorizationHeader, Map<String, Boolean> roles, User user, CreateAccountRequest request) throws Exception {
        // Validation
        boolean isValid = createAccountValidator.setRequest(request).isValid();
        if (!isValid){
            throw new Exception("Request does not pass validation");
        }

        if (user.getRole().equalsIgnoreCase("seller")){
            List<Account> sellerAcc = repository.findByUserId(user.getId());
            if(!sellerAcc.isEmpty()){
                throw new Exception("Seller already have an account");
            }
        }

        Account acc = Account.builder()
                .userId(request.getUserId())
                .bank(request.getBank())
                .number(request.getNumber())
                .name(request.getName())
                .type(request.getType())
                .balance(BigDecimal.ZERO)
                .build();

        return repository.save(acc);
    }

    public List<Account> getByUserId(String authorizationHeader, Map<String, Boolean> roles, User user){
        // Check token, get the user by token
        return repository.findByUserId(user.getId());
    }

    public Account getById(String authorizationHeader, Map<String, Boolean> roles, User user, String accountNumber) throws Exception {
        Optional<Account> opt = repository.findByNumber(accountNumber);
        if (opt.isEmpty()){
            throw new Exception("Account not found");
        }

        Account acc = opt.get();
        if (!acc.getUserId().equals(user.getId())){
            throw new Exception("Unauthorized user");
        }

        return acc;
    }

    public Account update(String authorizationHeader, Map<String, Boolean> roles, User user, int id, CreateAccountRequest request) throws Exception {
        // Validation
        boolean isValid = createAccountValidator.setRequest(request).isValid();
        if (!isValid){
            throw new Exception("Request does not pass validation");
        }

        Optional<Account> optAcc = repository.findById(id);
        if (optAcc.isEmpty()){
            throw new Exception("Account not found");
        }

        Account acc = optAcc.get();

        if (!acc.getUserId().equals(user.getId())){
            throw new Exception("Unauthorized user");
        }

        acc.setBank(request.getBank());
        acc.setNumber(request.getNumber());
        acc.setName(request.getName());
        acc.setType(request.getType());

        return repository.save(acc);
    }

    public void delete(String authorizationHeader, Map<String, Boolean> roles, User user, int id) throws Exception {
        boolean idExist = repository.existsById(id);
        if (!idExist){
            throw new Exception("Account not found");
        }
        repository.deleteById(id);
    }

    public Account topUpBalance(String authorizationHeader, Map<String, Boolean> roles, User user, TopUpBalanceRequest request) throws Exception {
        if (request.getBalance().compareTo(new BigDecimal("10000")) <= 0){
            throw new Exception("Top up balance must be greater than Rp. 10.000,00");
        }

        Optional<Account> optionalAccount = repository.findByNumber(request.getAccountNumber());

        if (optionalAccount.isEmpty()){
            throw new Exception("Account not found");
        }

        Account acc = optionalAccount.get();

        // User id diganti oleh header
        if(!acc.getUserId().equals(user.getId())){
            throw new Exception("Unauthorized user");
        }

        acc.setBalance(acc.getBalance().add(request.getBalance()));

        repository.save(acc);

        return acc;
    }
}
