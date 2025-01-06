package com.jpacourse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class VisitTO implements Serializable
{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    private List<String> medicalTreatmentsList;

    private String doctorFirstName;

    private String doctorLastName;

    public LocalDateTime getLocalDateTime()
    {
        return localDateTime;
    }

    public List<String> getMedicalTreatmentsList()
    {
        return medicalTreatmentsList;
    }

    public String getDoctorFirstName()
    {
        return doctorFirstName;
    }

    public String getDoctorLastName()
    {
        return doctorLastName;
    }

    public void setLocalDateTime(LocalDateTime time)
    {
        this.localDateTime = time;
    }

    public void setMedicalTreatmentsList(List<String> list)
    {
        this.medicalTreatmentsList = list;
    }

    public void setDoctorFirstName(String name)
    {
        this.doctorFirstName = name;
    }

    public void setDoctorLastName(String name)
    {
        this.doctorLastName = name;
    }
}
