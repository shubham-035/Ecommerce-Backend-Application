package com.payment_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    private String orderId;
    private String email;
    private String customerName;
    private String phone;
    private Double amount;
    private String status;
}