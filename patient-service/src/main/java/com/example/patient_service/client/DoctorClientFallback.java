package com.example.patient_service.client;

import com.example.patient_service.dtos.DoctorDTO;
import com.example.patient_service.dtos.DoctorSpecialtyDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DoctorClientFallback implements DoctorClient {

    @Override
    public DoctorDTO getDoctorById(Long idDoctor) {

        DoctorDTO fallbackDoctor = new DoctorDTO();
        fallbackDoctor.setIdDoctor(idDoctor);
        fallbackDoctor.setFirstName("N/A");
        fallbackDoctor.setLastName("N/A");
        fallbackDoctor.setLicenceNumber("Unavailable");
        fallbackDoctor.setSpecialty("Unavailable");
        return fallbackDoctor;
    }
    @Override
    public List<DoctorSpecialtyDTO> getDoctorBySpecialty(String specialty) {
        // fallback para getDoctorBySpecialty
        return Collections.emptyList();
    }
}

