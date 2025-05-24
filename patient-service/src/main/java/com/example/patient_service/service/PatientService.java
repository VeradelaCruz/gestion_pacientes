package com.example.patient_service.service;

import com.example.patient_service.dtos.PatientPatchDTO;
import com.example.patient_service.models.Patient;
import com.example.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public List<Patient> createPatient(List<Patient> patients){
        return patientRepository.saveAll(patients);
    }

    public List<Patient> showAllPatients(){
        return patientRepository.findAll();
    }

    public Patient showPatientById(Long idPatient) {
        return patientRepository.findById(idPatient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));
    }


    public Patient updatePatient(Long idPatient, Patient patient){
        Patient updatedPatient = showPatientById(idPatient);
        updatedPatient.setFirstName(patient.getFirstName());
        updatedPatient.setLastName(patient.getLastName());
        updatedPatient.setEmail(patient.getEmail());
        updatedPatient.setGender(patient.getGender());
        updatedPatient.setAddress(patient.getAddress());
        updatedPatient.setPhoneNumber(patient.getPhoneNumber());
        updatedPatient.setBloodType(patient.getBloodType());
        updatedPatient.setDateOfBirth(patient.getDateOfBirth());

        return patientRepository.save(updatedPatient);
    }

    public Patient patchPatient(Long idPatient, PatientPatchDTO patientPatchDTO){
        Patient patient= showPatientById(idPatient);
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
        Patient patient= showPatientById(idPatient);
        patientRepository.delete(patient);
    }

    public List<Patient> showByName(String firstName) {
        List<Patient> patients = patientRepository.findByFirstName(firstName);
        if (patients.isEmpty()) {
            throw new RuntimeException("No doctors found with firstName: " + firstName);
        }
        return patients;
    }


    public List<Patient> showByLastName(String lastName){
        List<Patient> patients = patientRepository.findByLastName(lastName);
        if (patients.isEmpty()) {
            throw new RuntimeException("No doctors found with lastName: " + lastName);
        }
        return patients;
    }

}
