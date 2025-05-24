package com.example.appointment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;

    private PatientDTO patient;
    private DoctorDTO doctor;
}
