package com.example.appointment_service.repository;

import com.example.appointment_service.models.Appointment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long > {

    @Query("SELECT a FROM Appointment a WHERE a.idDoctor = :idDoctor")
    List<Appointment> findByDoctor(@Param("idDoctor") Long idDoctor);

    @Query("SELECT a FROM Appointment a WHERE a.idPatient = :idPatient")
    List<Appointment> findByPatient(@Param("idPatient") Long idPatient);
}
