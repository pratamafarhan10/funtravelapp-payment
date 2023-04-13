package com.funtravelapp.payment.controller.transaction;

import com.funtravelapp.payment.model.transaction.Transaction;
import com.funtravelapp.payment.service.transaction.TransactionService;
import com.funtravelapp.payment.service.transaction.TransactionStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") int id, @RequestParam("status") TransactionStatusEnum status) {
        try {
            return ResponseEntity.ok(service.updateStatus(id, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
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
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
