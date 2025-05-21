package com.labwork.planning.kafka;

import com.labwork.planning.model.Notification;
import com.labwork.planning.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "plan-topic", groupId = "notification-group")
    public void listen(String message) {
        Notification notification = Notification.builder()
                .message(message)
                .build();
        notificationRepository.save(notification);
        System.out.println("✅ Получено сообщение из Kafka: " + message);
    }
}
