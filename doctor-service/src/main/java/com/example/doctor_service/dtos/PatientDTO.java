package com.example.doctor_service.dtos;

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
    private String bloodType;
    private LocalDate dateOfBirth;
    private Long doctorId;
}
