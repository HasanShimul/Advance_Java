package com.example.Java_Final_Assignment.service;

import com.example.Java_Final_Assignment.dto.PerformanceRequestDTO;
import com.example.Java_Final_Assignment.dto.PerformanceResponseDTO;
import com.example.Java_Final_Assignment.repository.BonusRepository;
import com.example.Java_Final_Assignment.repository.EmployeeRepository;
import com.example.Java_Final_Assignment.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerformanceService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PerformanceRepository performanceRepo;

    @Autowired
    private BonusRepository bonusRepo;

    @Transactional
    public PerformanceResponseDTO calculate(PerformanceRequestDTO req) {

        var employee = employeeRepo.findById(req.employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }

        validate(req);

        int total = req.taskCompletion + req.attendance + req.teamCollaboration +
                req.problemSolving + req.communication +
                req.leadership + req.clientSatisfaction;

        String category;
        double percentage;

        if (total >= 90) {
            category = "Gold";
            percentage = 20;
        } else if (total >= 75) {
            category = "Silver";
            percentage = 12;
        } else if (total >= 60) {
            category = "Bronze";
            percentage = 5;
        } else {
            category = "None";
            percentage = 0;
        }

        double bonus = employee.baseSalary * percentage / 100;
        double totalComp = employee.baseSalary + bonus;

        performanceRepo.save(req, total);
        bonusRepo.save(req.employeeId, req.reviewYear, total, category, percentage, bonus, totalComp);

        PerformanceResponseDTO res = new PerformanceResponseDTO();
        res.totalScore = total;
        res.category = category;
        res.bonusAmount = bonus;
        res.totalCompensation = totalComp;

        return res;
    }

    private void validate(PerformanceRequestDTO r) {
        if (r.taskCompletion > 25 || r.attendance > 15 || r.teamCollaboration > 15 ||
                r.problemSolving > 15 || r.communication > 10 ||
                r.leadership > 10 || r.clientSatisfaction > 10) {
            throw new RuntimeException("Invalid KPI score");
        }
    }
}