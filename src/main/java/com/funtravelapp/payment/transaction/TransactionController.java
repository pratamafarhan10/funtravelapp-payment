package com.funtravelapp.payment.transaction;

import com.funtravelapp.payment.transaction.dto.PaymentRequest;
import com.funtravelapp.payment.responseMapper.ResponseMapper;
import com.funtravelapp.payment.role.RoleService;
import com.funtravelapp.payment.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService service;
    @Autowired
    RoleService roleService;

    @KafkaListener(
            topics = "UpdateStatusPayment",
            groupId = "UpdateStatusPayment-1"
    )
    public void create(String data) {
        service.create(data);
    }

    @PostMapping("/payment/{chainingId}")
    public ResponseEntity<?> payment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId, @RequestBody PaymentRequest request) {
        try {
            return ResponseMapper.ok(null, service.payment(authorizationHeader, this.roleService.getCustomer(), null, chainingId, request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @KafkaListener(
            topics = "UpdateNotifStatus",
            groupId = "UpdateNotifStatus-1"
    )
    public void updateNotifStatus(String data) {
        service.updateNotifStatus(data);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            return ResponseMapper.ok(null, service.getAllByUserId(authorizationHeader, this.roleService.getCustomerAndSeller(),null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("/{chainingId}")
    public ResponseEntity<?> getById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId) {
        try {
            return ResponseMapper.ok(null, service.getById(authorizationHeader, this.roleService.getCustomerAndSeller(),null, chainingId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @PostMapping("/retry-email/{chainingId}")
    public ResponseEntity<?> retrySendEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId) {
        try {
            return ResponseMapper.ok(null, service.retrySendEmail(authorizationHeader,this.roleService.getCustomerAndSeller(), null, chainingId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }
}
