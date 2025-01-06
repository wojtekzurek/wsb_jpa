package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private EntityManager entityManager;

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
        //then
        //assertThat()
    }

}
