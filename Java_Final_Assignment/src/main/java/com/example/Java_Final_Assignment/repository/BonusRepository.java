package com.example.Java_Final_Assignment.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BonusRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void save(Long eid, int year, int score,
                     String category, double percent,
                     double bonus, double total) {

        String sql = """
        INSERT INTO bonus_records
        (employee_id, review_year, total_kpi_score,
         category, bonus_percentage, bonus_amount, total_compensation)
        VALUES
        (:eid, :year, :score, :cat, :per, :bonus, :total)
        """;

        MapSqlParameterSource p = new MapSqlParameterSource();
        p.addValue("eid", eid);
        p.addValue("year", year);
        p.addValue("score", score);
        p.addValue("cat", category);
        p.addValue("per", percent);
        p.addValue("bonus", bonus);
        p.addValue("total", total);

        jdbc.update(sql, p);
    }
}
