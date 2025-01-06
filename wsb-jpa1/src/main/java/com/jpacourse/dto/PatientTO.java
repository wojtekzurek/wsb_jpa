package com.jpacourse.dto;

import com.jpacourse.persistence.entity.AddressEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PatientTO implements Serializable
{
    private Long id;

    private String firstName;

    private String lastName;

    private String telephoneNumber;

    private String email;

    private String patientNumber;

    private LocalDate dateOfBirth;

    private AddressEntity addressEntity;

    private List<VisitTO> visitsList;

    private Boolean isAdult;

    public Long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPatientNumber()
    {
        return patientNumber;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public AddressEntity getAddressEntity()
    {
        return addressEntity;
    }

    public List<VisitTO> getVisitsList()
    {
        return visitsList;
    }

    public Boolean getIsAdult()
    {
        return isAdult;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setFirstName(String name)
    {
        this.firstName = name;
    }

    public void setLastName(String name)
    {
        this.lastName = name;
    }

    public void setTelephoneNumber(String number)
    {
        this.telephoneNumber = number;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPatientNumber(String number)
    {
        this.patientNumber = number;
    }

    public void setDateOfBirth(LocalDate date)
    {
        this.dateOfBirth = date;
    }

    public void setAddressEntity(AddressEntity address)
    {
        this.addressEntity = address;
    }

    public void setVisitsList(List<VisitTO> list)
    {
        this.visitsList = list;
    }

    public void setIsAdult(Boolean isAdult)
    {
        this.isAdult = isAdult;
    }
}
