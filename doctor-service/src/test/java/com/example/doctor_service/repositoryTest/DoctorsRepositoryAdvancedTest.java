package com.example.doctor_service.repositoryTest;

import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.repository.DoctorsRepository;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class DoctorsRepositoryAdvancedTest {

   // @Autowired
    //private DoctorsService doctorService;

    @Autowired
    private DoctorsRepository doctorRepository;

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
