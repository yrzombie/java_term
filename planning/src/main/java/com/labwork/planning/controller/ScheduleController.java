package com.labwork.planning.controller;

import com.labwork.planning.model.Schedule;
import com.labwork.planning.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final SchedulingService schedulingService;

    @PostMapping("/{projectId}")
    public Schedule schedule(@PathVariable Long projectId) {
        return schedulingService.scheduleProject(projectId);
    }
}
