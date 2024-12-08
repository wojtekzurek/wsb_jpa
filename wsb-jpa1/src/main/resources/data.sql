insert into address (id, address_line1, address_line2, city, postal_code)
            values (1, 'xx', 'yy', 'city', '62-030');

insert into address (id, address_line1, address_line2, city, postal_code)
values (2, 'Rynek', 'przy fosie', 'Liberty City', '55-080');

insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
values (1, 'Sebastian', 'Lewandowski', '500123456', 's.lewy@pzpn.pl', 'P158', '1910-05-12', 1);

insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
values (2, 'Maciej', 'Blicharski', '987654321', 'm.blich@wp.pl', 'P159', '1985-09-17', 2);

insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
values (1, "Jerzy", 'Miszczuk', '456789123', 'jerz@dobromed.pl', 'D987', 'INTERNIST', 1);

insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
values (2, "Marzena", 'Wolt', '741852963', 'marz@dobromed.pl', 'D369', 'CHIRURGIST', 2);

insert into visit (id, description, time, patient_id, doctor_id)
values (1, "HEADACHE", '2024-12-08 08:00:00', 1, 1);

insert into visit (id, description, time, patient_id, doctor_id)
values (2, "SKIN PROBLEM", '2024-12-30 11:35:00', 2, 2);

insert into medical_treatment (id, description, type, visit_id)
values (1, 'SOLUTION', 'MEDICINE', 1);

insert into medical_treatment (id, description, type, visit_id)
values (2, 'OUTDOOR EXCERCISES', 'ACTIVITY', 2);