package com.example.doctor_service.repository;

import com.example.doctor_service.models.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorsRepository extends JpaRepository<Doctors, Long> {

    @Query("SELECT d FROM Doctors d WHERE LOWER(d.specialty)= LOWER(:specialty) ")
    List<Doctors> findBySpecialty(@Param("specialty") String specialty);

    @Query("SELECT d FROM Doctors d WHERE LOWER(d.firstName) = LOWER(:firstName)")
    List<Doctors> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT d FROM Doctors d WHERE LOWER(d.lastName) = LOWER(:lastName)")
    List<Doctors> findByLastName(@Param("lastName") String lastName);

}
