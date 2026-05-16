package com.example.Java_Final_Assignment.repository;

import com.example.Java_Final_Assignment.dto.PerformanceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void save(PerformanceRequestDTO r, int total) {

        String sql = """
        INSERT INTO performance_reviews
        (employee_id, review_year, task_completion, attendance,
        team_collaboration, problem_solving, communication,
        leadership, client_satisfaction, total_score)
        VALUES
        (:eid, :year, :tc, :at, :tcb, :ps, :com, :ld, :cs, :total)
        """;

        MapSqlParameterSource p = new MapSqlParameterSource();
        p.addValue("eid", r.employeeId);
        p.addValue("year", r.reviewYear);
        p.addValue("tc", r.taskCompletion);
        p.addValue("at", r.attendance);
        p.addValue("tcb", r.teamCollaboration);
        p.addValue("ps", r.problemSolving);
        p.addValue("com", r.communication);
        p.addValue("ld", r.leadership);
        p.addValue("cs", r.clientSatisfaction);
        p.addValue("total", total);

        jdbc.update(sql, p);
    }
}