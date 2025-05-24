package com.example.appointment_service.feignClient;

import com.example.appointment_service.dtos.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {
    @GetMapping("/patient/getPatientById/{idPatient}")
    PatientDTO getPatientById(@PathVariable("idPatient")Long idPatient);
}
