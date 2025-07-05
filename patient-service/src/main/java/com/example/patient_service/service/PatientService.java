package com.example.patient_service.service;

import com.example.patient_service.client.DoctorClient;
import com.example.patient_service.dtos.*;
import com.example.patient_service.mapper.PatientMapper;
import com.example.patient_service.models.Patient;
import com.example.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorClient doctorClient;

    @Autowired
    PatientMapper patientMapper;

    public Patient createPatient(Patient patient){
        if (patient.getIdDoctor() == null) {
            throw new RuntimeException("Doctor ID is required");
        }
        try {
            doctorClient.getDoctorById(patient.getIdDoctor());
        } catch (Exception e) {
            throw new RuntimeException("Doctor with ID " + patient.getIdDoctor() + " not found");
        }
        return patientRepository.save(patient);
    }

    public List<Patient> showAllPatients(){
        return patientRepository.findAll();
    }

    public PatientResponseDTO showPatientWithDoctor(Long idPatient) {
        Patient patient = patientRepository.findById(idPatient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));

        DoctorDTO doctor = doctorClient.getDoctorById(patient.getIdDoctor());

        PatientResponseDTO responseDTO = new PatientResponseDTO();
        responseDTO.setIdPatient(patient.getIdPatient());
        responseDTO.setFirstName(patient.getFirstName());
        responseDTO.setLastName(patient.getLastName());
        responseDTO.setEmail(patient.getEmail());
        responseDTO.setPhoneNumber(patient.getPhoneNumber());
        responseDTO.setGender(patient.getGender());
        responseDTO.setAddress(patient.getAddress());
        responseDTO.setDateOfBirth(patient.getDateOfBirth());
        responseDTO.setBloodType(patient.getBloodType());
        responseDTO.setDoctor(doctor);

        return responseDTO;

    }
    public Patient getPatientEntityById(Long idPatient) {
        return patientRepository.findById(idPatient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));
    }


    public Patient updatePatient(Long idPatient, Patient patient){
        Patient updatedPatient = getPatientEntityById(idPatient);
        updatedPatient.setFirstName(patient.getFirstName());
        updatedPatient.setLastName(patient.getLastName());
        updatedPatient.setEmail(patient.getEmail());
        updatedPatient.setGender(patient.getGender());
        updatedPatient.setAddress(patient.getAddress());
        updatedPatient.setPhoneNumber(patient.getPhoneNumber());
        updatedPatient.setBloodType(patient.getBloodType());
        updatedPatient.setDateOfBirth(patient.getDateOfBirth());
        updatedPatient.setDoctorId(patient.getIdDoctor());

        return patientRepository.save(updatedPatient);
    }

    public Patient patchPatient(Long idPatient, PatientPatchDTO patientPatchDTO){
        Patient patient= getPatientEntityById(idPatient);
        if (patientPatchDTO.getFirstName() != null) {
            patient.setFirstName(patientPatchDTO.getFirstName());
        }
        if (patientPatchDTO.getLastName() != null) {
            patient.setLastName(patientPatchDTO.getLastName());
        }
        if (patientPatchDTO.getEmail() != null) {
            patient.setEmail(patientPatchDTO.getEmail());
        }
        if (patientPatchDTO.getGender() != null) {
            patient.setGender(patientPatchDTO.getGender());
        }
        if (patientPatchDTO.getAddress() != null) {
            patient.setAddress(patientPatchDTO.getAddress());
        }
        if (patientPatchDTO.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientPatchDTO.getPhoneNumber());
        }
        if (patientPatchDTO.getBloodType() != null) {
            patient.setBloodType(patientPatchDTO.getBloodType());
        }
        if (patientPatchDTO.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientPatchDTO.getDateOfBirth());
        }


        return patientRepository.save(patient);
    }

    public void removePatient(Long idPatient){
        Patient patient= getPatientEntityById(idPatient);
        patientRepository.delete(patient);
    }

    public List<Patient> showByName(String firstName) {
        List<Patient> patients = patientRepository.findByFirstName(firstName);
        if (patients.isEmpty()) {
            throw new RuntimeException("No patient found with firstName: " + firstName);
        }
        return patients;
    }


    public List<Patient> showByLastName(String lastName){
        List<Patient> patients = patientRepository.findByLastName(lastName);
        if (patients.isEmpty()) {
            throw new RuntimeException("No patient found with lastName: " + lastName);
        }
        return patients;
    }

    public List<DoctorSpecialtyDTO> showDoctorSpecialty(String specialty){
        List<DoctorSpecialtyDTO> doctorSpecialtyDTOS = doctorClient.getDoctorBySpecialty(specialty);
        return doctorSpecialtyDTOS;

    }

    public List<PatientDTO> showPatientDTOByDoctorId(Long idDoctor) {
        List<Patient> patients = patientRepository.findByDoctorId(idDoctor);
        if (patients == null || patients.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No patients found for doctor with ID: " + idDoctor
            );
        }
        return patientMapper.toDtoList(patients);
    }







}
