package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;
import java.util.List;

import java.util.stream.Collectors;

public class VisitMapper
{
    public static VisitTO ToMapper(final VisitEntity visitEntity)
    {
        if(visitEntity != null)
        {
            VisitTO visit = new VisitTO();

            visit.setLocalDateTime(visitEntity.getTime());
            visit.setDoctorFirstName(visitEntity.getDoctorEntity().getFirstName());
            visit.setDoctorLastName(visitEntity.getDoctorEntity().getLastName());

            if(visitEntity.getMedicalThreatmentsEntityList() != null)
            {
                List<String> medicalTreatmentsList = visitEntity.getMedicalThreatmentsEntityList()
                                .stream()
                                .map(t -> t.getType().name())
                                .collect(Collectors.toList());
                visit.setMedicalTreatmentsList(medicalTreatmentsList);
            }

            return visit;
        }
        return null;
    }
}
