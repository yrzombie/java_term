package com.labwork.planning.service;

import com.labwork.planning.kafka.KafkaMessageProducer;
import com.labwork.planning.model.Project;
import com.labwork.planning.model.Schedule;
import com.labwork.planning.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final ProjectService projectService;
    private final ScheduleRepository scheduleRepository;
    private final KafkaMessageProducer kafkaProducer;

    public Schedule scheduleProject(Long projectId) {
        Project project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Проект не найден"));

        int total = project.getTasks().stream()
                .mapToInt(task -> task.getDuration())
                .sum();

        Schedule schedule = Schedule.builder()
                .projectId(project.getId())
                .projectName(project.getName())
                .totalDuration(total)
                .build();

        scheduleRepository.save(schedule);

        kafkaProducer.sendMessage("plan-topic", "Проект " + project.getName() + " запланирован. Длительность: " + total);

        return schedule;
    }
}
