package com.funtravelapp.payment.controller.transaction;

import com.funtravelapp.payment.dto.transaction.PaymentRequest;
import com.funtravelapp.payment.model.transaction.Transaction;
import com.funtravelapp.payment.responseMapper.ResponseMapper;
import com.funtravelapp.payment.service.transaction.TransactionService;
import com.funtravelapp.payment.service.transaction.TransactionStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService service;

    @KafkaListener(
            topics = "UpdateStatusPayment",
            groupId = "UpdateStatusPayment-1"
    )
    public void create(String data) {
        service.create(data);
    }

    @PostMapping("/update-status/{chainingId}")
    public ResponseEntity<?> payment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId, @RequestBody PaymentRequest request) {
        try {
            return ResponseMapper.ok(null, service.payment(authorizationHeader, null, chainingId, request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    //    @PostMapping("/update-invoice-status/{id}")
    @KafkaListener(
            topics = "UpdateNotifStatus",
            groupId = "UpdateNotifStatus-1"
    )
    public void updateInvoiceStatus(String data) {
        service.updateInvoiceStatus(data);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            return ResponseMapper.ok(null, service.getAllByUserId(authorizationHeader, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("/{chainingId}")
    public ResponseEntity<?> getById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId) {
        try {
            return ResponseEntity.ok(service.getById(authorizationHeader, null, chainingId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<?> retrySendEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId) {
        try {
            return ResponseEntity.ok(service.retrySendEmail(authorizationHeader, null, chainingId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
