package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>
{
    public void addPatientVisit(Long patientId, Long doctorId, LocalDateTime time, String description);

    public List<PatientEntity> getPatientByLastName(String name);

    public List<PatientEntity> getPatientByVisitsMoreThan(Long visitsDownLimit);

    public List<PatientEntity> getPatientByIsAdult(Boolean isAdult);
}
