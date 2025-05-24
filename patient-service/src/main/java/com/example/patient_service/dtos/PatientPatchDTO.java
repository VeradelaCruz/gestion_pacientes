package com.example.patient_service.dtos;

import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientPatchDTO {
    private Long idPatient;
    private String firstName;
    private  String lastName;
    @Email
    private String email;
    @Size(min = 10, max = 15, message = "The phone number must be between 10 y 15 characters")
    private String phoneNumber;
    private Gender gender;
    private String address;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;
}
