package com.example.doctor_service.serviceTest;

import com.example.doctor_service.client.PatientClient;
import com.example.doctor_service.models.AvailableDays;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.repository.DoctorsRepository;
import com.example.doctor_service.service.DoctorsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorsRepository doctorsRepository;

    @Mock
    private PatientClient patientClient;

    @InjectMocks
    private DoctorsService doctorsService;

    @Test
    void updateDoctor_whenValidDoctor_thenReturnsUpdatedDoctor() {
        // Arrange
        Long idDoctor = 4L;

        Doctors inputDoctor = new Doctors();
        inputDoctor.setFirstName("Laura");
        inputDoctor.setLastName("Gómez");
        inputDoctor.setEmail("laura.gomez@example.com");
        inputDoctor.setLicenceNumber("LIC12345");
        inputDoctor.setPhoneNumber("1234567890");
        inputDoctor.setSpecialty("Dermatology");
        inputDoctor.setAvailableDays(new AvailableDays(true, false, true, true, true, true, false));

        Doctors existingDoctor = new Doctors();
        existingDoctor.setIdDoctor(idDoctor);
        existingDoctor.setFirstName("Laura");
        existingDoctor.setLastName("Gómez");
        existingDoctor.setEmail("laura.old@example.com");
        existingDoctor.setLicenceNumber("LIC54321");
        existingDoctor.setPhoneNumber("1234567899");
        existingDoctor.setSpecialty("Dermatology");
        existingDoctor.setAvailableDays(new AvailableDays(false, false, false, false, false, false, false));

        when(doctorsRepository.findById(idDoctor)).thenReturn(Optional.of(existingDoctor));
        when(doctorsRepository.save(any(Doctors.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Doctors result = doctorsService.updateDoctor(idDoctor, inputDoctor);

        // Captura y verifica lo que se envió a save()
        ArgumentCaptor<Doctors> captor = ArgumentCaptor.forClass(Doctors.class);
        verify(doctorsRepository).save(captor.capture());
        Doctors savedDoctor = captor.getValue();

        // Verificaciones
        assertEquals("Laura", savedDoctor.getFirstName());
        assertEquals("Gómez", savedDoctor.getLastName());
        assertEquals("laura.gomez@example.com", savedDoctor.getEmail());
        assertEquals("1234567890", savedDoctor.getPhoneNumber());
        assertEquals("LIC12345", savedDoctor.getLicenceNumber());

        assertTrue(savedDoctor.getAvailableDays().isMonday());
        assertFalse(savedDoctor.getAvailableDays().isTuesday());
        assertTrue(savedDoctor.getAvailableDays().isWednesday());
        assertTrue(savedDoctor.getAvailableDays().isThursday());
        assertTrue(savedDoctor.getAvailableDays().isFriday());
        assertTrue(savedDoctor.getAvailableDays().isSaturday());
        assertFalse(savedDoctor.getAvailableDays().isSunday());

        verify(doctorsRepository).findById(idDoctor);
    }


    //Prueba unitaria del método showByLastName:
    //En caso de un médico existente:
    @Test
    void showByLastName_WhenDoctorExist_ReturnsDoctors(){
        //Arrange
        String lastName ="Ramirez";

        List<Doctors> doctorsList = new ArrayList<>();
        Doctors doctors1= new Doctors();
        doctors1.setLastName(lastName);

        Doctors doctors2= new Doctors();
        doctors2.setLastName(lastName);

       doctorsList.add(doctors1);
       doctorsList.add(doctors2);

        when(doctorsRepository.findByLastName(lastName)).thenReturn(doctorsList);

        //Act
        List<Doctors> resultList= doctorsService.showByLastName(lastName);

        //Assert
        assertFalse(resultList.isEmpty());
        assertEquals(2, resultList.size());
        assertEquals(lastName, resultList.get(0).getLastName());
        assertEquals(lastName, resultList.get(1).getLastName());

        //Verify
        verify(doctorsRepository).findByLastName(lastName);

    }

    //En caso de que no haya ningún médico con ese apellido:
    @Test
    void showByLastName_WhenDoctorDoesNotExist_ReturnsException(){
        //Arrange
        String lastName ="Mendez";
        List<Doctors> doctorsList= new ArrayList<>();

        when(doctorsRepository.findByLastName(lastName)).thenReturn(new ArrayList<>());

        //Act + Assert

        RuntimeException e = assertThrows(RuntimeException.class, ()->
                doctorsService.showByLastName(lastName));

        assertEquals("No doctors found with lastName: " + lastName, e.getMessage());

        // Verificar que se llamó al repositorio con ese parámetro:
        verify(doctorsRepository).findByLastName(lastName);


    }

    //Prueba unitaria del método createDoctor:
    //  Usando ArgumentCaptor
    @Test
    void createDoctor_ThenReturnDoctor(){
        //Arrange:
        List<Doctors> doctorsList= new ArrayList<>();
        Doctors doctors= new Doctors();
        doctors.setFirstName("Ana");
        doctors.setLastName("Gonzalez");

        doctorsList.add(doctors);

        // Act
        doctorsService.createDoctor(doctorsList);

        // ArgumentCaptor para lista de Doctors (cast necesario)
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);


        // Verificamos que se llamó a saveAll y capturamos la lista pasada
        verify(doctorsRepository).saveAll(captor.capture());

        // Obtenemos la lista capturada
        List<Doctors> capturedDoctorsList = captor.getValue();

        // Assert
        assertFalse(capturedDoctorsList.isEmpty());
        assertEquals("Ana", capturedDoctorsList.get(0).getFirstName());
        assertEquals("Gonzalez", capturedDoctorsList.get(0).getLastName());
    }

    //Prueba unitaria del método showDoctorById:
    //En caso de encontrar un doctor don el id dado:
    @Test
    void showDoctorById_WhenDoctorExists_ThenReturnDoctor(){
        //Arrange:
        Long id =3L;
        Doctors doctors =new Doctors();
        doctors.setIdDoctor(id);

        when(doctorsRepository.findById(id)).thenReturn(Optional.of(doctors));

        //Act
        Doctors result= doctorsService.showDoctorById(id);

        //Assert:
        assertNotNull(result);
        assertEquals(doctors.getIdDoctor(),result.getIdDoctor());

        //Verify:
        verify(doctorsRepository).findById(id);

    }

    //En caso de no encontrar un doctor:
    @Test
    void showDoctorById_whenDoctorMissing_thenThrowsNotFound() {
        // Arrange
        Long id = 3L;
        when(doctorsRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                doctorsService.showDoctorById(id)
        );

        // Verificamos el estado y el mensaje de la excepción
        assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException) exception).getStatusCode()); //getStatusCode() devuelve un objeto HttpStatus.
        assertEquals("Patient not found.", exception.getReason());                        // getReason() devuelve el mensaje definido.

        // Verificamos que se llamó al repositorio
        verify(doctorsRepository).findById(id);
    }


}

