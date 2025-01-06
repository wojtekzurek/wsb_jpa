package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.*;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.persistence.enums.TreatmentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Test
    @Transactional
    public void testShouldGetPatientById()
    {
        //given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("street1");
        addressEntity.setAddressLine2("street2");
        addressEntity.setCity("City");
        addressEntity.setPostalCode("00-000");

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("NAME");
        doctorEntity.setLastName("SURNAME");
        doctorEntity.setEmail("EMAIL");
        doctorEntity.setTelephoneNumber("123456789");
        doctorEntity.setDoctorNumber("D000");
        doctorEntity.setSpecialization(Specialization.SURGEON);
        doctorEntity.setAddressEntity(addressEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("NAME");
        patientEntity.setLastName("SURNAME");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setPatientNumber("P000");
        patientEntity.setEmail("EMAIL");
        patientEntity.setDateOfBirth(LocalDate.of(2000, 1, 1));
        patientEntity.setIsAdult(true);
        patientEntity.setAddressEntity(addressEntity);

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setDoctorEntity(doctorEntity);
        visitEntity.setPatientEntity(patientEntity);
        visitEntity.setDescription("DESCRIPTION");
        visitEntity.setTime(LocalDateTime.parse("2000-01-01 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        MedicalTreatmentEntity medicalTreatmentEntity = new MedicalTreatmentEntity();
        medicalTreatmentEntity.setDescription("DESCRIPTION1");
        medicalTreatmentEntity.setType(TreatmentType.RTG);

        visitEntity.setMedicalThreatmentsEntityList(List.of(medicalTreatmentEntity));
        patientEntity.setVisitEntityList(List.of(visitEntity));

        patientDao.save(patientEntity);

        //when
        PatientTO patientTo = patientService.getById(patientEntity.getId());

        //then
        assertThat(patientTo).isNotNull();
        assertThat(patientTo.getPatientNumber()).isEqualTo(patientEntity.getPatientNumber());
        assertThat(patientTo.getFirstName()).isEqualTo(patientEntity.getFirstName());
        assertThat(patientTo.getLastName()).isEqualTo(patientEntity.getLastName());
        assertThat(patientTo.getEmail()).isEqualTo(patientEntity.getEmail());
        assertThat(patientTo.getTelephoneNumber()).isEqualTo(patientEntity.getTelephoneNumber());
        assertThat(patientTo.getDateOfBirth()).isEqualTo(patientEntity.getDateOfBirth());
        assertThat(patientTo.getIsAdult()).isEqualTo(patientEntity.getIsAdult());
        assertThat(patientTo.getVisitsList()).isNotEmpty();
        assertThat(patientTo.getAddressEntity().getAddressLine1()).isEqualTo(addressEntity.getAddressLine1());
        assertThat(patientTo.getAddressEntity().getAddressLine2()).isEqualTo(addressEntity.getAddressLine2());
        assertThat(patientTo.getAddressEntity().getCity()).isEqualTo(addressEntity.getCity());
        assertThat(patientTo.getAddressEntity().getPostalCode()).isEqualTo(addressEntity.getPostalCode());

        patientTo.getVisitsList().forEach(visit -> {
            assertThat(visit.getLocalDateTime()).isEqualTo(visitEntity.getTime());
            assertThat(visit.getDoctorFirstName()).isEqualTo(visitEntity.getDoctorEntity().getFirstName());
            assertThat(visit.getDoctorLastName()).isEqualTo(visitEntity.getDoctorEntity().getLastName());
            assertThat(visit.getMedicalTreatmentsList()).contains(String.valueOf(TreatmentType.RTG));
        });
    }
}
