

USE hospital_db;

-- OPTIONAL: clear existing data before reloading the smaller set
DELETE FROM appointments;
DELETE FROM beds;
DELETE FROM wards;
DELETE FROM doctors;
DELETE FROM patients;

-- 1. PATIENTS (25 records)
INSERT IGNORE INTO patients (patient_id, name, age, gender, contact, address) VALUES
(1, 'Ananya Gupta', 51, 'Male', '9181590830', '89 Park Lane, Mumbai'),
(2, 'Aarohi Verma', 31, 'Male', '9860913909', '600 Park Lane, Mumbai'),
(3, 'Vivaan Mehta', 6, 'Male', '9462819482', '106 Lake View, Mumbai'),
(4, 'Riya Nair', 25, 'Female', '9181909378', '438 Church Street, Mumbai'),
(5, 'Anika Reddy', 59, 'Female', '9432319487', '897 Church Street, Mumbai'),
(6, 'Rahul Singh', 37, 'Male', '9186252760', '986 MG Road, Mumbai'),
(7, 'Varun Kumar', 74, 'Female', '9559797114', '486 MG Road, Mumbai'),
(8, 'Vivaan Iyer', 40, 'Female', '9465075291', '506 MG Road, Mumbai'),
(9, 'Reyansh Rao', 37, 'Male', '9366712768', '285 Station Road, Mumbai'),
(10, 'Aarohi Chopra', 71, 'Female', '9656321223', '675 Station Road, Mumbai'),
(11, 'Aarav Singh', 76, 'Male', '9440268599', '327 Station Road, Mumbai'),
(12, 'Nikhil Chopra', 66, 'Male', '9786666176', '64 Station Road, Mumbai'),
(13, 'Aditya Mehta', 57, 'Male', '9159010928', '104 Church Street, Mumbai'),
(14, 'Saanvi Sharma', 10, 'Male', '9962459571', '119 Park Lane, Mumbai'),
(15, 'Anika Singh', 62, 'Female', '9121547280', '211 Lake View, Mumbai'),
(16, 'Diya Gupta', 70, 'Male', '9841485253', '546 Lake View, Mumbai'),
(17, 'Varun Kumar', 43, 'Male', '9933633875', '749 MG Road, Mumbai'),
(18, 'Aarav Rao', 36, 'Female', '9439575513', '105 Station Road, Mumbai'),
(19, 'Ishita Mehta', 44, 'Male', '9799075116', '802 Station Road, Mumbai'),
(20, 'Ishita Malhotra', 23, 'Female', '9516761222', '29 Station Road, Mumbai'),
(21, 'Riya Malhotra', 60, 'Male', '9997528820', '15 MG Road, Mumbai'),
(22, 'Kavya Iyer', 18, 'Female', '9330434839', '334 Church Street, Mumbai'),
(23, 'Myra Jain', 17, 'Male', '9579868282', '537 Lake View, Mumbai'),
(24, 'Aarav Chopra', 57, 'Male', '9902227918', '64 Church Street, Mumbai'),
(25, 'Kunal Kumar', 68, 'Female', '9180334018', '464 Lake View, Mumbai');

-- 2. DOCTORS (5 records)
INSERT INTO doctors (doctor_id, name, specialization, available_days, available_time) VALUES
(1, 'Dr. Anil Kulkarni', 'Cardiologist', 'Mon,Wed,Fri', '10:00-14:00'),
(2, 'Dr. Sunita Rao', 'Pediatrician', 'Mon,Tue,Thu', '09:00-13:00'),
(3, 'Dr. Ramesh Iyer', 'Orthopedic', 'Tue,Thu,Sat', '11:00-15:00'),
(4, 'Dr. Kavita Desai', 'Gynecologist', 'Mon,Wed,Fri', '14:00-18:00'),
(5, 'Dr. Suresh Nair', 'General Physician', 'Mon,Tue,Wed,Thu,Fri', '09:00-17:00');

-- 3. WARDS (3 records)
INSERT INTO wards (ward_id, ward_name, ward_type) VALUES
(1, 'General Ward A', 'General'),
(2, 'ICU Ward', 'ICU'),
(3, 'Maternity Ward', 'Maternity');

-- 4. BEDS (8 records)
INSERT INTO beds (bed_id, ward_id, bed_number, status) VALUES
(1, 1, 'G01', 'Available'),
(2, 1, 'G02', 'Occupied'),
(3, 1, 'G03', 'Available'),
(4, 1, 'G04', 'Available'),
(5, 2, 'I01', 'Occupied'),
(6, 2, 'I02', 'Occupied'),
(7, 3, 'M01', 'Available'),
(8, 3, 'M02', 'Available');

-- 5. APPOINTMENTS (20 records)
INSERT INTO appointments (appt_id, patient_id, doctor_id, appt_date, appt_time, status) VALUES
(1, 17, 5, '2026-08-26', '16:00:00', 'Completed'),
(2, 8, 5, '2026-08-09', '17:00:00', 'Cancelled'),
(3, 7, 4, '2026-08-05', '15:00:00', 'Scheduled'),
(4, 15, 3, '2026-08-03', '12:00:00', 'Scheduled'),
(5, 7, 3, '2026-08-26', '10:00:00', 'Cancelled'),
(6, 5, 3, '2026-08-05', '13:00:00', 'Cancelled'),
(7, 15, 2, '2026-08-24', '10:00:00', 'Scheduled'),
(8, 16, 2, '2026-08-22', '12:00:00', 'Scheduled'),
(9, 14, 5, '2026-08-13', '14:00:00', 'Scheduled'),
(10, 12, 3, '2026-08-03', '14:00:00', 'Scheduled'),
(11, 18, 4, '2026-08-15', '09:00:00', 'Scheduled'),
(12, 17, 5, '2026-08-10', '17:00:00', 'Cancelled'),
(13, 4, 2, '2026-08-04', '10:00:00', 'Scheduled'),
(14, 2, 2, '2026-08-09', '11:00:00', 'Completed'),
(15, 22, 3, '2026-08-13', '11:00:00', 'Completed'),
(16, 17, 5, '2026-08-16', '14:00:00', 'Scheduled'),
(17, 2, 2, '2026-08-14', '10:00:00', 'Scheduled'),
(18, 1, 1, '2026-08-26', '13:00:00', 'Scheduled'),
(19, 8, 1, '2026-08-09', '10:00:00', 'Scheduled'),
(20, 11, 5, '2026-08-14', '13:00:00', 'Completed');
