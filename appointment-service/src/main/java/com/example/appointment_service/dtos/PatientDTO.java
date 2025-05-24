package com.example.appointment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long idPatient;
    private String firstName;
    private String lastName;
    private String email;
}
