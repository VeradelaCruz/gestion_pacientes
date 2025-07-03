package com.example.patient_service.client;

import com.example.patient_service.dtos.DoctorDTO;
import com.example.patient_service.dtos.DoctorSpecialtyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service", url = "http://localhost:8081",fallback = DoctorClientFallback.class) // suponiendo que corre en ese puerto
public interface DoctorClient {
    @GetMapping("/doctor/getDoctorById/{idDoctor}")
    DoctorDTO getDoctorById(@PathVariable("idDoctor") Long idDoctor);

    @GetMapping("doctor/findBySpecialty/{specialty}")
    List<DoctorSpecialtyDTO> getDoctorBySpecialty(@PathVariable("specialty")String specialty);

}