package com.example.doctor_service.client;


import com.example.doctor_service.dtos.PatientDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PatientClientFallback implements PatientClient{
    public List<PatientDTO> getPatientsByDoctorId(Long doctorId) {
        // Mensaje por defecto o lista vacía
        System.out.println("Fallback: Patient service is not available. Returning empty list.");
        return Collections.emptyList();
    }


}
