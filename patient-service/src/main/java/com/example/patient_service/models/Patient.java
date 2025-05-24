package com.example.patient_service.models;

import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatient;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private  String lastName;
    @Email
    private String email;
    @Size(min = 10, max = 15, message = "The phone number must be between 10 y 15 characters")
    @Column(name = "phone_number")
    private String phoneNumber;
    private Gender gender;
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;


}
