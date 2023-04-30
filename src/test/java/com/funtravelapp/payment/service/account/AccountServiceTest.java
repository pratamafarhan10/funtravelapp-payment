package com.funtravelapp.payment.service.account;

import com.funtravelapp.payment.controller.account.AccountController;
import com.funtravelapp.payment.model.account.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AccountController.class)
class AccountServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Test
    public void createShouldReturnObject() throws Exception {
//        Account acc = new Account(1, "BCA", "000123", "John Doe", "DEBIT");
//        Mockito.when(service.create(acc)).thenReturn(acc);
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/account/create")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
    }
}