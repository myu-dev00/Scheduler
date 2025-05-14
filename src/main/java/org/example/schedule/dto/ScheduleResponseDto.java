package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.Schedule;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String contents;
    private String username;
    private String date;

    // Entity → DTO 변환 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.username = schedule.getUsername();
        this.date = schedule.getDate();
    }
}
