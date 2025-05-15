package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto createSchedule(Schedule schedule); // 일정 생성
    List<ScheduleResponseDto> findAllSchedules(); //전체 조회
    Optional<Schedule> findScheduleById(Long id); //단건 조회
    int updateSchedule(Long id, String contents, String username);// 일정 수정
    int deleteSchedule(Long id); //일정 삭제
}