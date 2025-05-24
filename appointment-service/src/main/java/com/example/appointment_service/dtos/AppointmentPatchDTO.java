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
    @NotNull(message = "Appointment date is required")

    private LocalDate appointmentDate;
    @NotNull(message = "Appointment time is required")

    private LocalTime appointmentTime;

    @NotBlank(message = "Reason is required")
    @Size(max = 60, message = "Reason must be 60 characters or less")
    private String reason;
    @NotNull(message = "Appointment status is required")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Long patientId;
    private Long doctorId;
}
