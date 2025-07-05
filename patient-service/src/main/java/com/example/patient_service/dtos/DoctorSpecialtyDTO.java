package com.example.patient_service.dtos;

import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSpecialtyDTO {

        private Long idDoctor;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Specialty is required")
        private String specialty;

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getSpecialty() {
                return specialty;
        }

        public void setSpecialty(String specialty) {
                this.specialty = specialty;
        }

        public Long getIdDoctor() {
                return idDoctor;
        }

        public void setIdDoctor(Long idDoctor) {
                this.idDoctor = idDoctor;
        }
}
