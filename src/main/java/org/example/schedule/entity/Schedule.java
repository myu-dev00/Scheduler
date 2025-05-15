package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {

    private Long id; //식별 ID
    private String contents; //할 일
    private String username; //작성자명
    private String password; //비밀번호
    private String date; //작성일
    private String modifiedDate; //수정일

    //생성자 - 수정일의 경우엔 처음부터 포함되지 않음.
    public Schedule(String contents, String username, String password, String date) {
        this.contents = contents;
        this.username = username;
        this.password = password;
        this.date = date;
    }

    //내용 수정시 사용되는 update메서드, 수정날짜가 포함된다.
    public void update(String contents, String username) {
        this.contents = contents;
        this.username = username;
        this.modifiedDate = LocalDate.now().toString();
    }
}