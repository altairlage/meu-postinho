package com.fiap.meu_postinho_notifier.adapters.kafka;

import com.fiap.meu_postinho_notifier.dtos.SendCreateVisitNotification;
import com.fiap.meu_postinho_notifier.services.SendNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final String LOG_TAG = "[KAFKA CONSUMER] - ";
    private final SendNotificationService notificationService;

    public KafkaConsumer(SendNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification")
    public void notifyVisit(SendCreateVisitNotification sendCreateVisitNotification, Acknowledgment acknowledgment) {
        logger.info(LOG_TAG + "Notify visit event received!");
        try{
            logger.info(LOG_TAG + "Sending notification...");
            notificationService.sendNotification(sendCreateVisitNotification);
            logger.info(LOG_TAG + "Notification sent successfully!");
            acknowledgment.acknowledge();
        } catch (Exception e){
            logger.error(LOG_TAG + "Error processing event: ", e);
            logger.error(e.getMessage());
        }
    }
}
