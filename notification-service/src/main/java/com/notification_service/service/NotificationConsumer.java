package com.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_service.dto.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private final EmailService emailService;
    private final SmsService smsService;
    private final WhatsappService whatsappService;

    public NotificationConsumer(EmailService emailService, SmsService smsService, WhatsappService whatsappService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.whatsappService = whatsappService;
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consume(String message) {
        try {

            PaymentEvent event = objectMapper.readValue(message, PaymentEvent.class);
            System.out.println("Payment Received");
            System.out.println(event.getOrderId());
            String body =
                    "Hello "
                            + event.getCustomerName()
                            + ", your payment of ₹"
                            + event.getAmount()
                            + " is SUCCESS.";

            // Email
            emailService.sendEmail(event.getEmail(), "Payment Successful", body);

            // SMS
            smsService.sendSms(event.getPhone(), body);

            // WhatsApp
            whatsappService.sendWhatsapp(event.getPhone(), body);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}