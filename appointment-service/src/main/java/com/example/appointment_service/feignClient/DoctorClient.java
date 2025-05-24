package com.example.appointment_service.feignClient;

import com.example.appointment_service.dtos.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorClient {
    @GetMapping("/doctor/getDoctorById/{idDoctor}")
    DoctorDTO getDoctorById(@PathVariable("idDoctor") Long idDoctor);
}
