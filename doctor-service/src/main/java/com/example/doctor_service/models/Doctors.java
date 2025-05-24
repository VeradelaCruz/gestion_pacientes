package com.example.doctor_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;



@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoctor;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private  String lastName;
    @Email
    private String email;
    @Size(min = 10, max = 15, message = "The phone number must be between 10 y 15 characters")
    @Column(name = "phone_number")
    private String phoneNumber;
    @Size(min = 6, max = 15, message = "The licence number must be between 6 y 15 characters")
    @Column(name = "licence_number")
    private String licenceNumber;
    @Embedded
    @Column(name = "available_days")
    private AvailableDays availableDays;
    private String specialty;

}
