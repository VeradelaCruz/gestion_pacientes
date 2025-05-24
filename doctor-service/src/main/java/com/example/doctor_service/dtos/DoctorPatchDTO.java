package com.example.doctor_service.dtos;
import com.example.doctor_service.models.AvailableDays;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatchDTO {
    private Long idDoctor;
    private String firstName;
    private  String lastName;
    @Email
    private String email;
    @Size(min = 10, max = 15, message = "The phone number must be between 10 y 15 characters")
    private String phoneNumber;
    @Size(min = 6, max = 15, message = "The licence number must be between 6 y 15 characters")
    private String licenceNumber;
    private AvailableDays availableDays;
}
