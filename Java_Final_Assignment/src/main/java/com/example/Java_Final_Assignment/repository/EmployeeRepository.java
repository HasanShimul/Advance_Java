package com.example.Java_Final_Assignment.repository;

import com.example.Java_Final_Assignment.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Employee findById(Long id) {
        String sql = "SELECT * FROM employees WHERE employee_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.query(sql, params, rs -> {
            if (rs.next()) {
                Employee e = new Employee();
                e.employeeId = rs.getLong("employee_id");
                e.baseSalary = rs.getDouble("base_salary");
                return e;
            }
            return null;
        });
    }
}
