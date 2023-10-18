package com.example.exercicio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "customer_id")
    private Long customerId;

    // Construtores, getters e setters


    public OrderId() {
    }

    public OrderId(String orderNumber, Long customerId) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
