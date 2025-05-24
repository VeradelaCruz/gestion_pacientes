package com.example.appointment_service.models;

import com.example.appointment_service.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_appointment")
    private Long idAppointment;
    @NotNull(message = "Appointment date is required")
    @Column(name="appointment_date")
    private LocalDate appointmentDate;
    @NotNull(message = "Appointment time is required")
    @Column(name="appointment_time")
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
