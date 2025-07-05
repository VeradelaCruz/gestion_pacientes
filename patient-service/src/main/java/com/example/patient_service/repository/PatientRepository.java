package com.example.patient_service.repository;

import com.example.patient_service.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT d FROM Patient d WHERE LOWER(d.firstName) = LOWER(:firstName)")
    List<Patient> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT d FROM Patient d WHERE LOWER(d.lastName) = LOWER(:lastName)")
    List<Patient> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT d FROM Patient d WHERE d.idDoctor = :idDoctor")
    List<Patient> findByDoctorId(@Param("idDoctor")Long idDoctor);
}
