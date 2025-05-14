package org.example.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleRequestDto {
    private String title;
    private String username;
    private String password;
    private String date;
}
