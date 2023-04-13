package com.funtravelapp.payment.kafka.dto;

import java.math.BigDecimal;

public class UpdateStatusPaymentDTO {
    private Integer userId;
    private Integer orderId;
    private BigDecimal amount;
    private String status;

    public UpdateStatusPaymentDTO(Integer userId, Integer orderId, BigDecimal amount, String status) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
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
}
