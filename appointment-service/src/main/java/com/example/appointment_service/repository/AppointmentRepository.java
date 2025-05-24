package com.example.appointment_service.repository;

import com.example.appointment_service.enums.AppointmentStatus;
import com.example.appointment_service.models.Appointment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long > {

    @Query("SELECT a FROM Appointment a WHERE a.doctorId = :doctorId")
    List<Appointment> findByDoctor(@Param("doctorId") Long idDoctor);

    @Query("SELECT a FROM Appointment a WHERE a.patientId = :patientId")
    List<Appointment> findByPatient(@Param("patientId") Long idPatient);

    @Query("SELECT a FROM Appointment a WHERE a.status = :status")
    List<Appointment> findCompletedAppointments(@Param("status") AppointmentStatus status);

}
