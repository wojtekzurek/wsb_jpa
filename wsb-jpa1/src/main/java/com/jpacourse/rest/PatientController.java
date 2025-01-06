package com.jpacourse.rest;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService)
    {
        this.patientService = patientService;
    }

    @GetMapping("/patient/{id}")
    PatientTO getById(@PathVariable Long id)
    {
        PatientTO patient = patientService.getById(id);
        if(patient != null)
        {
            return patient;
        }
        throw new EntityNotFoundException("ERROR: PATIENT SERVICE NOT FOUND {id: " + id + "}");
    }

}
