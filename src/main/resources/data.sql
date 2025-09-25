INSERT INTO patient (name, email, gender, birth_date, blood_group)
VALUES
('Amrat', 'amrat@gmail.com', 'Male', '1999-6-27', 'B_POSITIVE'),
('Kailash', 'kailash@gmail.com', 'Male', '2003-09-20', 'B_POSITIVE'),
('Ibad', 'ibad@gmail.com', 'Male', '2001-5-25', 'B_POSITIVE'),
('Airaf', 'airaf@gmail.com', 'Male', '2004-02-3', 'AB_NEGATIVE'),
('Noman', 'noman@gmail.com', 'Male', '2000-09-20', 'AB_NEGATIVE');


INSERT INTO doctor (name, specialization, email)
VALUES
('Doctor 1', 'Cardiology', 'doctor1@gmail.com'),
('Doctor 2', 'Dermatology', 'doctor2@gmail.com'),
('Doctor 3', 'Orthopedics', 'doctor3@gmail.com');



INSERT INTO appointment (appointment_time, reason, doctor_id, patient_id)
VALUES
('2025-10-1 10:30:00', 'General checkup', 1, 2),
('2025-10-2 12:30:00', 'Skin Rashes', 2, 3),
('2025-10-3 15:30:00', 'Knee pain', 1, 1),
('2025-10-4 16:30:00', 'Regular checkup', 3, 3),
('2025-10-5 10:30:00', 'Consultation', 3, 4),
('2025-10-6 14:30:00', 'Skin treatment', 2, 5);
