package com.example.patient_service.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long idDoctor;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 6, max = 15, message = "The licence number must be between 6 and 15 characters")
    private String licenceNumber;

    @NotBlank(message = "Specialty is required")
    private String specialty;
}

