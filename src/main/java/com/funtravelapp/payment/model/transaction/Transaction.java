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
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "account_id")
    private Integer accountId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "is_invoice_sent")
    private String isInvoiceSent;

    public Transaction() {
    }

    public Transaction(Integer userId, Integer orderId, Integer accountId, BigDecimal amount, String status, LocalDate date, String isInvoiceSent) {
        this.userId = userId;
        this.orderId = orderId;
        this.accountId = accountId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
