package com.funtravelapp.payment.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "seller_id")
    private Integer sellerId;
    @Column(name = "customer_acc")
    private String customerAcc;
    @Column(name = "seller_acc")
    private String sellerAcc;
    @Column(name = "chaining_id")
    private String chainingId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "is_invoice_sent")
    private String isInvoiceSent;
}
