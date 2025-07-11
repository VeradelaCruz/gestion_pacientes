package com.example.doctor_service.repositoryTest;

import com.example.doctor_service.models.AvailableDays;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.repository.DoctorsRepository;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DoctorsRepositoryAdvancedTest {

    @Autowired
    private DoctorsRepository doctorRepository;


    @BeforeEach
    void clean() {
        doctorRepository.deleteAll(); // Limpia la tabla antes de cada test
    }


    @Test
    void shouldSaveDoctorWithAvailableDays() {
        AvailableDays days = new AvailableDays();
        days.setMonday(true);
        days.setWednesday(true);

        Doctors doctor = new Doctors();
        doctor.setFirstName("Ana");
        doctor.setLastName("Suárez");
        doctor.setEmail("ana@email.com");
        doctor.setPhoneNumber("1234567890");
        doctor.setLicenceNumber("ABC123");
        doctor.setSpecialty("Cardiology");
        doctor.setAvailableDays(days);

        doctorRepository.save(doctor);

        Assertions.assertEquals(1, doctorRepository.findAll().size());
    }

    @Test
    void shouldFindBySpecialty(){
        //Arrange:
        Doctors doctor1 =new Doctors();
        Doctors doctor2 =new Doctors();
        doctor1.setSpecialty("Neurology");
        doctor2.setSpecialty("Neurology");

        List<Doctors> doctorsList = List.of(doctor1, doctor2);
        doctorRepository.saveAll(doctorsList);


        //Act
        List<Doctors> doctorResult= doctorRepository.findBySpecialty("Neurology");

        //Assert
        assertThat(doctorResult).hasSize(2);
        assertThat(doctorResult).extracting("specialty").containsOnly("Neurology");

    }




}
