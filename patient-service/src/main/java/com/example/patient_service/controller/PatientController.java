package com.example.patient_service.controller;

import com.example.patient_service.dtos.PatientPatchDTO;
import com.example.patient_service.models.Patient;
import com.example.patient_service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    @PostMapping("/addPatient")
    public List<Patient> addPatient (@RequestBody List<Patient> patientList){
        return patientService.createPatient(patientList);
    }

    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients(){
        return patientService.showAllPatients();
    }

    @GetMapping("/getPatientById/{idPatient}")
    public Patient getPatientById(@PathVariable Long idPatient){
        return patientService.showPatientById(idPatient);
    }

    @PutMapping("/updatePatient/{idPatient}")
    public ResponseEntity<Patient> changePatient(@PathVariable Long idPatient,
                                                @Valid @RequestBody Patient patient){
        Patient updatedPatient=patientService.updatePatient(idPatient, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @PatchMapping("/updatePatchPatient/{idPatient}")
    public ResponseEntity<Patient> changePatchDoctor(@PathVariable Long idPatient,
                                                     @Valid @RequestBody PatientPatchDTO patientPatchDTO){
        Patient updatedPatient= patientService.patchPatient(idPatient, patientPatchDTO);
        return ResponseEntity.ok(updatedPatient);

    }

    @DeleteMapping("/deletePatient/{idPatient}")
    public ResponseEntity<?> deletePatient(@PathVariable Long idPatient){
        patientService.removePatient(idPatient);
        return ResponseEntity.ok("Patient with id: " + idPatient + " removed successfully");


    }


    @GetMapping("/findByName/{firstName}")
    public ResponseEntity<?> getPatientByName(@PathVariable String firstName){
        List<Patient> patients = patientService.showByName(firstName);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/findByLastName/{lastName}")
    public ResponseEntity<?> getPatientByLastName(@PathVariable String lastName){
        List<Patient> patients = patientService.showByLastName(lastName);
        return ResponseEntity.ok(patients);
    }
}
