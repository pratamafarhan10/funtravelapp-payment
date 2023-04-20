package com.funtravelapp.payment.controller.account;

import com.funtravelapp.payment.dto.account.CreateAccountRequest;
import com.funtravelapp.payment.model.account.Account;
import com.funtravelapp.payment.responseMapper.ResponseMapper;
import com.funtravelapp.payment.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateAccountRequest request) {
        try {
            return ResponseMapper.ok(null, accountService.create(request));
        }catch (Exception e){
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseMapper.ok(null, accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        try {
            return ResponseMapper.ok(null, accountService.getById(id));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody CreateAccountRequest account){
        try{
            return ResponseMapper.ok(null, accountService.update(id, account));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        try {
            accountService.delete(id);
            return ResponseMapper.ok(null, null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }
}
