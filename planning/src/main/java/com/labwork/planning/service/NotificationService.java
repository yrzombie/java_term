package com.labwork.planning.service;

import com.labwork.planning.model.Notification;
import com.labwork.planning.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "plan-topic", groupId = "notification-group")
    public void listen(String message) {
        Notification notification = Notification.builder()
                .message(message)
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
