package com.meu_postinho_api.producer;

import com.meu_postinho_api.dtos.SendVisitNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerApplication {

    private final Logger log = LoggerFactory.getLogger(KafkaProducerApplication.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendVisitMessage(SendVisitNotification sendNotification) {
        kafkaTemplate.send("notification", sendNotification);
        log.info("sent: {}", sendNotification);
    }

}
