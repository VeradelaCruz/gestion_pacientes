package com.example.doctor_service.controllerTest;

import com.example.doctor_service.controller.DoctorsController;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;


import java.util.List;


@WebMvcTest(DoctorsController.class)
@ActiveProfiles("test")
public class DoctorsControllerAdvancedTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("deprecation")
    @MockBean
    private DoctorsService doctorsService;

    @Test
    @DisplayName("GET /getAllDoctors devuelve lista de doctores y status 200")
    void shouldReturnListOfDoctors(){
        //Arrange
        Doctors doctor1= new Doctors();
        doctor1.setLastName("Mendez");

        Doctors doctor2= new Doctors();
        doctor1.setLastName("Ramirez");

        List<Doctors> doctorsList= List.of(doctor1,doctor2);
        when(doctorsService.showAllDoctors()).thenReturn(doctorsList);

    }
}
