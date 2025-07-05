package com.example.doctor_service.client;

import com.example.doctor_service.dtos.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-service", url = "http://localhost:8082",fallback = PatientClientFallback.class)
public interface PatientClient {
    @GetMapping("/patient/findPatientByDoctorId/{idDoctor}")
    List<PatientDTO> getPatientsByDoctorId(@PathVariable("idDoctor") Long idDoctor);
}

