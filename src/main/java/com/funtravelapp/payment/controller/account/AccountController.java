package com.funtravelapp.payment.controller.account;

import com.funtravelapp.payment.dto.account.CreateAccountRequest;
import com.funtravelapp.payment.dto.account.TopUpBalanceRequest;
import com.funtravelapp.payment.responseMapper.ResponseMapper;
import com.funtravelapp.payment.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody CreateAccountRequest request) {
        try {
            return ResponseMapper.ok(null, accountService.create(authorizationHeader, null, request));
        } catch (Exception e) {
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseMapper.ok(null, accountService.getByUserId(authorizationHeader, null));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getByAccountNumber(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("accountNumber") String accountNumber) {
        try {
            return ResponseMapper.ok(null, accountService.getById(authorizationHeader, null, accountNumber));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("id") int id, @RequestBody CreateAccountRequest account) {
        try {
            return ResponseMapper.ok(null, accountService.update(authorizationHeader, null, id, account));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("id") int id) {
        try {
            accountService.delete(authorizationHeader, null, id);
            return ResponseMapper.ok(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @PostMapping("/top-up/balance")
    public ResponseEntity<?> topUpBalance(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody TopUpBalanceRequest request) {
        try {
            return ResponseMapper.ok(null, accountService.topUpBalance(authorizationHeader, null, request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }
}
