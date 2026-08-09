package com.notification_service.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.whatsappNumber}")
    private String from;

    public void sendWhatsapp(
            String to,
            String body) {

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + from),
                body
        ).create();

        System.out.println("WhatsApp Sent Successfully");
    }
}