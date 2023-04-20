package com.funtravelapp.payment.kafka.dto;

public class CreateNotifDTO {
    private Integer orderId;
    private Integer userId;

    public CreateNotifDTO(Integer orderId, Integer userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
