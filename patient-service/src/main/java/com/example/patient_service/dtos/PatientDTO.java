package com.example.patient_service.dtos;

import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {private Long idPatient;
    private String firstName;
    private  String lastName;
    private BloodType bloodType;
    private LocalDate dateOfBirth;
    private Long doctorId;
}
