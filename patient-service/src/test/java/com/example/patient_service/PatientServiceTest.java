package com.example.patient_service;

import com.example.patient_service.client.DoctorClient;
import com.example.patient_service.dtos.DoctorDTO;
import com.example.patient_service.dtos.DoctorSpecialtyDTO;
import com.example.patient_service.enums.BloodType;
import com.example.patient_service.enums.Gender;
import com.example.patient_service.models.Patient;
import com.example.patient_service.repository.PatientRepository;
import com.example.patient_service.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorClient doctorClient;

    @InjectMocks
    private PatientService patientService;

    //Prueba unitaria del metodo savePatient
    @Test
    void createPatient_WithValidDoctor_SavePatient() {
        // Arrange
        Patient patient = new Patient();
        patient.setIdDoctor(1L);
        patient.setFirstName("Lucia");
        patient.setLastName("González");
        patient.setEmail("lucia.gonzalez@example.com");
        patient.setPhoneNumber("1123456789");
        patient.setGender(Gender.FEMALE);
        patient.setAddress("Calle Falsa 123, Buenos Aires");
        patient.setDateOfBirth(LocalDate.of(1990, 4, 15));
        patient.setBloodType(BloodType.O_POSITIVE);

        DoctorDTO doctorMock = new DoctorDTO();
        doctorMock.setIdDoctor(Long.valueOf(1L));
        doctorMock.setFirstName("Ana");
        doctorMock.setLastName("Gonzalez");
        doctorMock.setLicenceNumber("LIC123456");
        doctorMock.setSpecialty("Cardiology");

        when(doctorClient.getDoctorById(1L)).thenReturn(doctorMock); // mock exitoso
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.createPatient(patient);

        //Assert
        assertNotNull(result);
        assertEquals("Lucia", patient.getFirstName());
        assertEquals("González", patient.getLastName());
        verify(doctorClient).getDoctorById(1L);
        verify(patientRepository).save(patient);
    }

    @Test
    void createPatient_withoutDoctor_Exception(){
        //Arrange
        Patient patient= new Patient();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            patientService.createPatient(patient);
        });
        //Act + Assert
        assertEquals("Doctor ID is required", ex.getMessage());
        verifyNoInteractions(doctorClient);
        verifyNoInteractions(patientRepository);

    }

    @Test
    void createPatient_withNoExistingDoctor_Exception(){
        //Arrange
        Patient patient= new Patient();
        patient.setIdDoctor(99L);

        when(doctorClient.getDoctorById(99L)).thenThrow(new RuntimeException("Not found"));

        //Assert + Act
        RuntimeException e =assertThrows(RuntimeException.class, ()->{
                    patientService.createPatient(patient);
        });


        assertEquals("Doctor with ID " + patient.getIdDoctor() +  "not found",
                        e.getMessage());
        verifyNoInteractions(doctorClient);
        verifyNoInteractions(patientRepository);

    }


    //Prueba unitaria del metodo getPatientEntityById:
    //En caso de que el paciente exista:
    @Test
    void getPatientEntityById_WhenPatientExists_ReturnsPatient(){
        //Arrage:
        Patient patient= new Patient();
        patient.setFirstName("Mateo");
        patient.setLastName("Ramírez");
        patient.setIdPatient(2L);

        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));

        //Act
        Patient result= patientService.getPatientEntityById(2L);

        //Assert
        assertNotNull(patient);
        assertEquals("Mateo", result.getFirstName());
        assertEquals("Ramírez", result.getLastName());
    }

    //En caso de que el paciente NO exista:
    @Test
    void getPatientEntityById_WhenPatientDoesNotExist_ReturnsException(){
        //Arrange:
        //Prepara el esenario
        Long patientId = 5L;

        //Simula el comportamiento del repositorio devolviendo algo vacio que es lo esperado para la excepcion:
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
            patientService.getPatientEntityById(patientId);
        });

        assertEquals("Patient not found", e.getReason());
        verify(patientRepository).findById(patientId);

    }

    //Prueba unitaria del metodo showByName:
    //En caso de haber:
    @Test
    void showByName_WhenPatientExists_ReturnsPatient(){
        //Arrange:
        List<Patient> patientList = new ArrayList<>();
        Patient patient = new Patient();
        patient.setFirstName("Valentina");
        patientList.add(patient);

        when(patientRepository.findByFirstName("Valentina")).thenReturn(patientList);

        //Act
        List<Patient> resultList= patientService.showByName("Valentina");

        //Assert
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());                                      // la lista no debe estar vacía
        assertEquals(1, resultList.size());                            // debe contener 1 paciente
        assertEquals("Valentina", resultList.get(0).getFirstName());  // el nombre debe coincidir

        // Verificar que se llamó al repositorio con ese parámetro:
        verify(patientRepository).findByFirstName("Valentina");
    }

    //En caso de que la lista esté vacia:
    @Test
    void showByName_WhenPatientListIsEmpty_ReturnsException(){
        //Arrange:
        String firstName = "Valentina";

        when(patientRepository.findByFirstName("Valentina")).thenReturn(new ArrayList<>());

        // Act & Assert
        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            patientService.showByName(firstName);
        });


        assertEquals("No patient found with firstName: " + firstName, e.getMessage());

        // Verificar que se llamó al repositorio con ese parámetro:
        verify(patientRepository).findByFirstName("Valentina");

    }

    //Prueba unitaria del metodo showDoctorSpecialty:
    @Test
    void showDoctorSpecialty_WhenDoctorExists_ReturnsPatientList(){
        //Arrange
        DoctorSpecialtyDTO doctorDTO = new DoctorSpecialtyDTO();
        doctorDTO.setIdDoctor(2L);
        doctorDTO.setLastName("Ramirez");
        doctorDTO.setSpecialty("Neurology");

        List<DoctorSpecialtyDTO> doctorSpecialtyDTOS = new ArrayList<>();
        doctorSpecialtyDTOS.add(doctorDTO);

        when(doctorClient.getDoctorBySpecialty("Neurology")).thenReturn(doctorSpecialtyDTOS);

        //Act
        List<DoctorSpecialtyDTO> doctorsList= patientService.showDoctorSpecialty("Neurology");

        //Assert
        assertNotNull(doctorsList);
        assertFalse(doctorsList.isEmpty());                      // la lista no debe estar vacía
        assertEquals(1, doctorsList.size());           // debe contener 1 paciente
        assertEquals("Neurology", doctorsList.get(0).getSpecialty());

        // Verificar que se llamó al repositorio con ese parámetro:
        verify(doctorClient).getDoctorBySpecialty("Neurology");
    }
}
