package org.example.schedule.dto;

import lombok.Getter;
import org.example.schedule.entity.Schedule;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String username;
    private String date;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.username = schedule.getUsername();
        this.date = schedule.getDate();
    }
}
