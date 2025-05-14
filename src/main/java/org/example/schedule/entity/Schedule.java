package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String title;        // 할일
    private String username;     // 작성자명
    private String password;     // 비밀번호
    private String date;         // 작성일/수정일 (YYYY-MM-DD HH:mm:ss 형태의 문자열로 처리)
}
