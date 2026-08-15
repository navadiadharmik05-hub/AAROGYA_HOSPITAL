CREATE DATABASE IF NOT EXISTS school_db;
USE school_db;


CREATE TABLE IF NOT EXISTS students (
    prn VARCHAR(20) PRIMARY KEY,       
    roll_no INT NOT NULL,             
    full_name VARCHAR(100) NOT NULL,  
    division VARCHAR(5) NOT NULL,      
    branch VARCHAR(50) NOT NULL,       
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert a single student record
INSERT IGNORE INTO students (prn, roll_no, full_name, division, branch) 
VALUES ('125BTEC1085', 35, 'Dharmik Jignesh Navadia', 'A', 'ECS');

TRUNCATE TABLE students;


INSERT INTO students (prn, roll_no, full_name, division, branch) 
VALUES 
    ('125BTEC1084', 34, 'Om Nathu Nathwani', 'A', 'ECS'),
    ('125BTEC1082', 32, 'Atharv Modak', 'A', 'ECS'),
    ('PRN2026002', 102, 'Priya Patel', 'A', 'Computer Science'),
    ('PRN2026003', 101, 'Rohan Mehta', 'B', 'Information Technology');
    
SELECT * FROM students;


SELECT * FROM students WHERE prn = '125BTEC1085';

SELECT * FROM students WHERE branch = 'Computer Science';

SELECT * FROM students WHERE division = 'A';

UPDATE students 
SET full_name = 'Dharmik Navadia', 
    roll_no = 36, 
    division = 'B', 
    branch = 'Computer Science' 
WHERE prn = '125BTEC1085';

DELETE FROM students 
WHERE prn = '125BTEC1085';
