package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.schedule.dto.ScheduleRequestDto;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String title;        // 할일
    private String username;     // 작성자명
    private String password;     // 비밀번호
    private String date;

    private String modifiedDate; // 수정일 (YYYY-MM-DD)

    public Schedule(Long id, String title, String username, String password, String date) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.date = date;
    }

    // 수정 기능
    public void update(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
        this.username = dto.getUsername();
        this.date = java.time.LocalDate.now().toString();
    }
}
