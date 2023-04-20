package com.funtravelapp.payment.model.transaction;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
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
    private Integer customerAcc;
    @Column(name = "seller_acc")
    private Integer sellerAcc;
    @Column(name = "chaining_id")
    private Integer chainingId;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "is_invoice_sent")
    private String isInvoiceSent;

    public Transaction() {
    }

    public Transaction(Integer customerId, Integer sellerId, Integer customerAcc, Integer sellerAcc, Integer chainingId, Integer amount, String status, LocalDate date, String isInvoiceSent) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.customerAcc = customerAcc;
        this.sellerAcc = sellerAcc;
        this.chainingId = chainingId;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.isInvoiceSent = isInvoiceSent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getCustomerAcc() {
        return customerAcc;
    }

    public void setCustomerAcc(Integer customerAcc) {
        this.customerAcc = customerAcc;
    }

    public Integer getSellerAcc() {
        return sellerAcc;
    }

    public void setSellerAcc(Integer sellerAcc) {
        this.sellerAcc = sellerAcc;
    }

    public Integer getChainingId() {
        return chainingId;
    }

    public void setChainingId(Integer chainingId) {
        this.chainingId = chainingId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIsInvoiceSent() {
        return isInvoiceSent;
    }

    public void setIsInvoiceSent(String isInvoiceSent) {
        this.isInvoiceSent = isInvoiceSent;
    }
}
