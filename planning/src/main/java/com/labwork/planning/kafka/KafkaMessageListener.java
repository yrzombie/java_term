package com.labwork.planning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaMessageListener {

    @KafkaListener(topics = "plan-topic", groupId = "notification-group")
    public void listen(String message) {
        log.info("Получено сообщение из Kafka: {}", message);
        // вызывать NotificationService и сохранять сообщение в БД
    }
}
