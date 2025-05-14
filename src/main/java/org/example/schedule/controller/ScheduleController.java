package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();
//생성
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto dto) {
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        Schedule schedule = new Schedule(scheduleId, dto.getTitle(), dto.getUsername(), dto.getPassword(), dto.getDate());

        scheduleList.put(scheduleId, schedule);

        return new ScheduleResponseDto(schedule);
    }


    // 단건 조회 기능
    @GetMapping("/{id}")
    public ScheduleResponseDto findScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);
        return new ScheduleResponseDto(schedule);
    }

    //전체 조회 기능
}
