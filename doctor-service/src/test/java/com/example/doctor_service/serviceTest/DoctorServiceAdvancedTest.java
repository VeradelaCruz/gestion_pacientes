package com.example.doctor_service.serviceTest;

import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.repository.DoctorsRepository;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
public class DoctorServiceAdvancedTest {

    @Autowired
    private DoctorsService doctorService;

    @Autowired
    private DoctorsRepository doctorRepository;

    @Test
    void testCreateDoctor_shouldSaveMultipleDoctors(){
        //Arrange
        Doctors doctor1= new Doctors();
        doctor1.setLastName("Ramirez");

        Doctors doctor2= new Doctors();
        doctor2.setLastName("Ortiz");

        List<Doctors> doctors = List.of(doctor1, doctor2);

        // Act
        List<Doctors> savedDoctors = doctorService.createDoctor(doctors);

        // Assert
        assertThat(savedDoctors).hasSize(2);
        assertThat(savedDoctors.get(0).getIdDoctor()).isNotNull();

        assertThat(savedDoctors.get(0).getLastName()).isEqualTo("Ramirez");
        assertThat(savedDoctors.get(1).getLastName()).isEqualTo("Ortiz");



    }

    @AfterEach
    void tearDown() {
        doctorRepository.deleteAll();
    }

}
