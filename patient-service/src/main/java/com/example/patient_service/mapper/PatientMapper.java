package com.example.patient_service.mapper;

import com.example.patient_service.dtos.PatientDTO;
import com.example.patient_service.models.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);
    List<PatientDTO> toDtoList(List<Patient> patients);
}
