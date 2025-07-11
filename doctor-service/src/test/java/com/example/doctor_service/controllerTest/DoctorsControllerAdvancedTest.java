package com.example.doctor_service.controllerTest;

import com.example.doctor_service.controller.DoctorsController;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;

@AutoConfigureMockMvc
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
    void shouldReturnListOfDoctors() throws Exception{
        //Arrange
        Doctors doctor1= new Doctors();
        doctor1.setLastName("Mendez");

        Doctors doctor2= new Doctors();
        doctor2.setLastName("Ramirez");

        List<Doctors> doctorsList= List.of(doctor1,doctor2);
        when(doctorsService.showAllDoctors()).thenReturn(doctorsList);

        //Act + Assert
        mockMvc.perform(get("/doctor/getAllDoctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].lastName").value("Mendez"))
                .andExpect(jsonPath("$[1].lastName").value("Ramirez"));

    }

    @Test
    @DisplayName("GET /getDoctorById devuelve un doctor por id}")
    void shouldReturnDoctorsById() throws Exception {
        Long id = 4L;
        Doctors doctor = new Doctors();
        doctor.setIdDoctor(id);
        doctor.setLastName("Mendez");

        when(doctorsService.showDoctorById(id)).thenReturn(doctor);

        mockMvc.perform(get("/doctor/getDoctorById/{idDoctor}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDoctor").value(4));
    }

    @Test
    @DisplayName("GET /getDoctorById devuelve error en caso de no encontrar un doctor por id}")
    void shouldReturn404ErrorWhenDoctorNotFound() throws Exception{
        //Arrange
        Long id = 7L;
        Doctors doctor= new Doctors();

        when(doctorsService.showDoctorById(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));
        //Act + Assert
        mockMvc.perform(get("/doctor/getDoctorById/{idDoctor}", id))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test básico para verificar mock")
    void testBasicMock() throws Exception {
        Doctors doctor = new Doctors();
        doctor.setIdDoctor(1L);
        doctor.setLastName("Test");

        when(doctorsService.showDoctorById(anyLong())).thenReturn(doctor);

        mockMvc.perform(get("/doctor/getDoctorById/{idDoctor}", 999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Test"));
    }

}
