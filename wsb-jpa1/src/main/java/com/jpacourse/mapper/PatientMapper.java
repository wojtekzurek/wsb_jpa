package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;
import java.util.List;

import java.util.stream.Collectors;

public class PatientMapper
{
    public static PatientTO ToMapper(final PatientEntity patientEntity)
    {
        if(patientEntity != null)
        {
            PatientTO patient = new PatientTO();

            patient.setId(patientEntity.getId());
            patient.setFirstName(patientEntity.getFirstName());
            patient.setLastName(patientEntity.getLastName());
            patient.setTelephoneNumber(patientEntity.getTelephoneNumber());
            patient.setEmail(patientEntity.getEmail());
            patient.setPatientNumber(patientEntity.getPatientNumber());
            patient.setDateOfBirth(patientEntity.getDateOfBirth());
            patient.setAddressEntity(patientEntity.getAddressEntity());
            patient.setIsAdult(patientEntity.getIsAdult());

            if(patientEntity.getVisitEntityList() != null)
            {
                List<VisitTO> visitToList = patientEntity.getVisitEntityList()
                        .stream()
                        .map(VisitMapper::ToMapper)
                        .collect(Collectors.toList());
                patient.setVisitsList(visitToList);
            }

            return patient;
        }
        return null;
    }

    public static PatientEntity EntityMapper(final PatientTO patientTo)
    {
        if(patientTo != null)
        {
            PatientEntity patientEntity = new PatientEntity();

            patientEntity.setId(patientTo.getId());
            patientEntity.setFirstName(patientTo.getFirstName());
            patientEntity.setLastName(patientTo.getLastName());
            patientEntity.setTelephoneNumber(patientTo.getTelephoneNumber());
            patientEntity.setEmail(patientTo.getEmail());
            patientEntity.setDateOfBirth(patientTo.getDateOfBirth());
            patientEntity.setPatientNumber(patientTo.getPatientNumber());
            patientEntity.setAddressEntity(patientTo.getAddressEntity());
            patientEntity.setIsAdult(patientTo.getIsAdult());

            return patientEntity;
        }
        return null;
    }
}
