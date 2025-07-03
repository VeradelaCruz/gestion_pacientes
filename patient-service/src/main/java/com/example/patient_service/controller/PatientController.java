package com.example.patient_service.controller;

import com.example.patient_service.dtos.DoctorSpecialtyDTO;
import com.example.patient_service.dtos.PatientDTO;
import com.example.patient_service.dtos.PatientPatchDTO;
import com.example.patient_service.dtos.PatientResponseDTO;
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
    public Patient addPatient (@RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients(){
        return patientService.showAllPatients();
    }

    @GetMapping("/getPatientById/{idPatient}")
    public ResponseEntity<PatientResponseDTO>getPatientById(@PathVariable Long idPatient) {
        PatientResponseDTO dto = patientService.showPatientWithDoctor(idPatient);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getPatientEntityById/{idPatient}")
    public ResponseEntity<PatientResponseDTO>getPatientEntityById(@PathVariable Long idPatient) {
        PatientResponseDTO dto = patientService.showPatientWithDoctor(idPatient);
        return ResponseEntity.ok(dto);
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

    @GetMapping("/findDoctorBySpecialty/{specialty}")
    public ResponseEntity<?> getDoctorBySpecialty(@PathVariable String specialty){
        List<DoctorSpecialtyDTO> doctorSpecialtyDTOS = patientService.showDoctorSpecialty(specialty);
        return ResponseEntity.ok(doctorSpecialtyDTOS);
    }

    @GetMapping("/findPatientByDoctorId/{doctorId}")
    public List<PatientDTO> getPatientsByDoctorId(@PathVariable Long doctorId) {
        return patientService.showPatientDTOByDoctorId(doctorId);
    }

}
