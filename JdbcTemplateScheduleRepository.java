package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
//JDBC 구현체
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //일정 생성 후 ID 반환
    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("contents", schedule.getContents());
        params.put("username", schedule.getUsername());
        params.put("password", schedule.getPassword());
        params.put("date", schedule.getDate());

        // DB에 저장 후 자동 생성된 PK(id)를 가져옴
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        schedule.setId(key.longValue());
        return new ScheduleResponseDto(schedule);
    }

    //전체 조회 (내림차순)
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT * FROM schedule ORDER BY date DESC";
        return jdbcTemplate.query(sql, scheduleRowMapperToDto());
    }

    // JDBC쿼리를 Schedule DTO객체로 변환
    private RowMapper<ScheduleResponseDto> scheduleRowMapperToDto() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("contents"),
                rs.getString("username"),
                rs.getString("date")
        );
    }

    //단건 조회
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperToEntity(), id);
        return result.stream().findAny();
    }
    // JDBC쿼리를 Schedule entity 객체로 변환
    private RowMapper<Schedule> scheduleRowMapperToEntity() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule(
                    rs.getString("contents"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("date")
            );
            schedule.setId(rs.getLong("id"));
            schedule.setModifiedDate(rs.getString("modified_date"));
            return schedule;
        };
    }

    //일정 수정 (제목,작성자명만, 날짜 수정일로 변경)
    @Override
    public int updateSchedule(Long id, String contents, String username) {
        return jdbcTemplate.update("UPDATE schedule SET contents = ?, username = ?, modified_date = ? WHERE id = ?",
                contents, username, LocalDate.now().toString(), id);
    }

    // 일정 삭제
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}