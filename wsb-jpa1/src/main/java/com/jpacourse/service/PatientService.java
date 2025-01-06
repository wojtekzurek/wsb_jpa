package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import org.springframework.stereotype.Service;

public interface PatientService {
    public PatientTO getById(Long id);
}
