package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules() {
        List<Schedule> sortedList = new ArrayList<>(scheduleList.values());
        // 날짜 기준 내림차순 정렬
        sortedList.sort(Comparator.comparing(Schedule::getDate).reversed());

        List<ScheduleResponseDto> responseList = new ArrayList<>();
        for (Schedule schedule : sortedList) {
            responseList.add(new ScheduleResponseDto(schedule));
        }
        return responseList;
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,
                                              @RequestParam String password,
                                              @RequestBody ScheduleRequestDto dto) {

        Schedule schedule = scheduleList.get(id);

        if (schedule == null) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 할일/작성자만 수정하고 수정일은 update 메서드에서 처리
        schedule.update(dto);

        return new ScheduleResponseDto(schedule);
    }
}
