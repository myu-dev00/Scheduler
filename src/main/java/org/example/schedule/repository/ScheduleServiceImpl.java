package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
//serviice 구현체
public class ScheduleServiceImpl {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //일정생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getContents(), dto.getUsername(), dto.getPassword(), dto.getDate());
        return scheduleRepository.createSchedule(schedule);
    }

    //전체조회
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    //단건 조회
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id)
                .map(ScheduleResponseDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 ID입니다."));
    }

    //일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String username) {
        int updated = scheduleRepository.updateSchedule(id, title, username);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정된 데이터가 없습니다.");
        }
        return findScheduleById(id);
    }

    //일정삭제
    @Transactional
    public void deleteSchedule(Long id) {
        int deleted = scheduleRepository.deleteSchedule(id);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 데이터가 없습니다.");
        }
    }
}