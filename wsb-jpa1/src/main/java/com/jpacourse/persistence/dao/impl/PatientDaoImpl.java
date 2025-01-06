package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addPatientVisit(Long patientId, Long doctorId, LocalDateTime time, String description)
    {
        PatientEntity patientEntity = entityManager.find(PatientEntity.class, patientId);
        if(patientEntity == null)
        {
            throw new EntityNotFoundException("ERROR: PATIENT NOT FOUND {id: " + patientId + "}");
        }

        DoctorEntity doctorEntity = entityManager.find(DoctorEntity.class, doctorId);
        if(doctorEntity == null)
        {
            throw new EntityNotFoundException("ERROR: DOCTOR NOT FOUND {id: " + doctorId + "}");
        }

        VisitEntity visit = new VisitEntity();
        visit.setPatientEntity(patientEntity);
        visit.setDoctorEntity(doctorEntity);
        visit.setTime(time);
        visit.setDescription(description);

        patientEntity.getVisitEntityList().add(visit);
        entityManager.merge(patientEntity);
    }
}
