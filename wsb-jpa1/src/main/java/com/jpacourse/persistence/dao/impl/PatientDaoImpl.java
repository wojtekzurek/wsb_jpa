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
import java.util.List;

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
        entityManager.persist(visit);

        patientEntity.getVisitEntityList().add(visit);
        entityManager.merge(patientEntity);
    }

    @Override
    public List<PatientEntity> getPatientByLastName(String name)
    {
        return entityManager.createQuery("SELECT patient FROM PatientEntity patient " +
                "WHERE patient.lastName LIKE :NAME", PatientEntity.class)
                .setParameter("NAME", "%"+name+"%")
                .getResultList();
    }

    @Override
    public List<PatientEntity> getPatientByVisitsMoreThan(Long visitsCount)
    {
        return entityManager.createQuery("SELECT patient FROM PatientEntity patient " +
                "JOIN patient.visitEntityList visit " +
                "GROUP BY patient " +
                "HAVING COUNT (visit) > :VISCOUNT", PatientEntity.class)
                .setParameter("VISCOUNT", visitsCount)
                .getResultList();
    }

    @Override
    public List<PatientEntity> getPatientByIsAdult(Boolean isAdult)
    {
        return entityManager.createQuery("SELECT patient FROM PatientEntity patient " +
                "WHERE patient.isAdult = :ISADULT", PatientEntity.class)
                .setParameter("ISADULT", isAdult)
                .getResultList();
    }
}
