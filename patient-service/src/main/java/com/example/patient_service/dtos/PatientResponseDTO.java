package com.example.patient_service.dtos;

import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private Long idPatient;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String address;
    private LocalDate dateOfBirth;
    private BloodType bloodType;

    private DoctorDTO doctor;  // aquí incluimos el doctor completo
}
