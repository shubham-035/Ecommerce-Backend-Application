package com.notification_service.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_service.dto.PaymentEvent;
import org.apache.kafka.common.serialization.Deserializer;

public class PaymentEventDeserializer
        implements Deserializer<PaymentEvent> {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public PaymentEvent deserialize(
            String topic,
            byte[] data) {

        try {

            if (data == null) {
                return null;
            }

            return objectMapper.readValue(
                    data,
                    PaymentEvent.class);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error deserializing JSON",
                    e);
        }
    }
}