package com.example.patient_service.dtos;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSpecialtyDTO {

        private Long idDoctor;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Specialty is required")
        private String specialty;

}
