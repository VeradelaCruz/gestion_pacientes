package com.example.appointment_service.dtos;

import com.example.appointment_service.enums.AppointmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentPatchDTO {
    private Long idAppointment;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Long patientId;
    private Long doctorId;
}
