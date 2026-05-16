CREATE TABLE IF NOT EXISTS employees (
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    designation VARCHAR(100) DEFAULT 'EMPLOYEE',
    base_salary DOUBLE,
    role VARCHAR(20),
    last_promotion_date DATE
    );

CREATE TABLE IF NOT EXISTS performance_reviews (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     employee_id BIGINT,
     review_year INT,
     task_completion INT,
     attendance INT,
     team_collaboration INT,
    problem_solving INT,
    communication INT,
    leadership INT,
    client_satisfaction INT,
    total_score INT,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
    );

CREATE TABLE IF NOT EXISTS bonus_records (
     bonus_id BIGINT PRIMARY KEY AUTO_INCREMENT,
     employee_id BIGINT,
    review_year INT,
    total_kpi_score INT,
    category VARCHAR(20),
    bonus_percentage DOUBLE,
    bonus_amount DOUBLE,
    total_compensation DOUBLE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
    );