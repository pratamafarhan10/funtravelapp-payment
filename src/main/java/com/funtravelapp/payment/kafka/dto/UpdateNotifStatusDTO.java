package com.funtravelapp.payment.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class UpdateNotifStatusDTO {
    private Integer orderId;
    private String status;

    public UpdateNotifStatusDTO(Integer orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
