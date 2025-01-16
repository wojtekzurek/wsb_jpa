package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory factory;

    @Transactional
    @Test
    public void testShouldGetAddresById()
    {
        //when
        PatientEntity patient = patientDao.findOne(1L);
        //then
        assertThat(patient.getFirstName()).isEqualTo("Sebastian");
        assertThat(patient.getLastName()).isEqualTo("Lewandowski");
        assertThat(patient.getTelephoneNumber()).isEqualTo("500123456");
        assertThat(patient.getEmail()).isEqualTo("s.lewy@pzpn.pl");
        assertThat(patient.getPatientNumber()).isEqualTo("P158");
        assertThat(patient.getDateOfBirth()).isEqualTo("1910-05-12");
    }

    @Transactional
    @Test
    public void testShouldDeletePatient()
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

        addressDao.save(addressEntity);
        patientEntity.setVisitEntityList(List.of(visitEntity));
        patientDao.save(patientEntity);

        //when
        patientDao.delete(patientEntity);

        //then
        assertThat(patientDao.findOne(patientEntity.getId())).isNull();
        assertThat(doctorEntity).isNotNull();
    }

    @Transactional
    @Test
    public void testShouldCheckPatientVisit()
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
        patientEntity.setVisitEntityList(new ArrayList<>());

        addressDao.save(addressEntity);
        entityManager.persist(doctorEntity);
        entityManager.persist(patientEntity);
        LocalDateTime testDate = LocalDateTime.of(2025, 1, 1, 10, 0, 0);

        //when
        patientDao.addPatientVisit(patientEntity.getId(), doctorEntity.getId(), testDate, "DESCRIPTION");

        //then
        PatientEntity testPatient = entityManager.find(PatientEntity.class, patientEntity.getId());
        assertThat(testPatient.getVisitEntityList()).isNotEmpty();

        VisitEntity testVisit = testPatient.getVisitEntityList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("TEST ERROR"));

        assertThat(testVisit.getTime()).isEqualTo(testDate);
        assertThat(testVisit.getDescription()).isEqualTo("DESCRIPTION");
        assertThat(testVisit.getPatientEntity()).isEqualTo(patientEntity);
        assertThat(testVisit.getDoctorEntity()).isEqualTo(doctorEntity);
    }

    @Transactional
    @Test
    public void testShouldGetPatientById()
    {
        //given
        String lastName = "Mickiewicz";

        //when
        List<PatientEntity> patientList = patientDao.getPatientByLastName(lastName);

        //then
        assertThat(patientList.size()).isEqualTo(1);
        assertThat(patientList.stream().sorted(Comparator.comparing(PatientEntity::getFirstName)).collect(Collectors.toList())
                .get(0).getFirstName()).isEqualTo("Adam");
        assertThat(patientList.stream().sorted(Comparator.comparing(PatientEntity::getId)).collect(Collectors.toList())
                .get(0).getId()).isEqualTo(3L);
    }

    @Transactional
    @Test
    public void testShouldGetPatientByVisitsCount()
    {
        //given
        Long visits = 1L;
        Long targetPatientId = 3L;

        //when
        List<PatientEntity> patientList = patientDao.getPatientByVisitsMoreThan(visits);

        //then
        assertThat(patientList.size()).isEqualTo(1);
        assertThat(patientList.get(0).getId()).isEqualTo(targetPatientId);
    }

    @Transactional
    @Test
    public void testShouldGetPatientByIsAdult()
    {
        //given
        int adultPatientsInBase = 2;
        int notAdultPatientsInBase = 1;

        //when
        List<PatientEntity> adultPatientList = patientDao.getPatientByIsAdult(true);
        List<PatientEntity> notAdultPatientList = patientDao.getPatientByIsAdult(false);

        //then
        assertThat(adultPatientList.size()).isEqualTo(adultPatientsInBase);
        assertThat(notAdultPatientList.size()).isEqualTo(notAdultPatientsInBase);
    }

    @Test
    public void testOptimistLockException()
    {
        //given
        EntityManager manager1 = factory.createEntityManager();
        EntityManager manager2 = factory.createEntityManager();

        AddressEntity address = new AddressEntity();
        address.setPostalCode("12-456");
        address.setCity("WROCLAW");
        address.setAddressLine1("STREET1");
        address.setAddressLine2("STREET2");

        EntityTransaction transaction1 = manager1.getTransaction();
        transaction1.begin();
        manager1.persist(address);
        transaction1.commit();
        manager1.clear();

        EntityTransaction transaction2 = manager2.getTransaction();
        transaction2.begin();
        AddressEntity addressTransaction2 = manager2.find(AddressEntity.class, address.getId());

        transaction1.begin();
        AddressEntity addressTransaction1 = manager1.find(AddressEntity.class, address.getId());
        addressTransaction1.setCity("NEW CITY");
        transaction1.commit();

        //when
        addressTransaction2.setCity("OLD CITY");

        //then
        try {
            transaction2.commit();
            fail("Expected OptimisticLockException, but it wasn't thrown.");
        } catch (RollbackException e){
            assertThat(e.getCause()).isInstanceOf(OptimisticLockException.class);
        } finally {
            manager1.close();
            manager2.close();
        }

    }

}
