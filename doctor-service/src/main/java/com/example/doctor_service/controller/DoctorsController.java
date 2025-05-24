package com.example.doctor_service.controller;

import com.example.doctor_service.dtos.DoctorPatchDTO;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.service.DoctorsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorsController {
    @Autowired
    DoctorsService doctorsService;

    @PostMapping("/addDoctor")
    public List<Doctors> addDoctor (@RequestBody List<Doctors> doctorsList){
        return doctorsService.createDoctor(doctorsList);
    }

    @GetMapping("/getAllDoctors")
    public List<Doctors> getAllDoctors(){
        return doctorsService.showAllDoctors();
    }

    @GetMapping("/getDoctorById/{idDoctor}")
    public Doctors getDoctorById(@PathVariable Long idDoctor){
        return doctorsService.showDoctorById(idDoctor);
    }

    @PutMapping("/updateDoctor/{idDoctor}")
    public ResponseEntity<Doctors> changeDoctor(@PathVariable Long idDoctor,
                                                @Valid @RequestBody Doctors doctors){
       Doctors updatedDoctor=doctorsService.updateDoctor(idDoctor, doctors);
       return ResponseEntity.ok(updatedDoctor);
    }

    @PatchMapping("/updatePatchDoctor/{idDoctor}")
    public ResponseEntity<Doctors> changePatchDoctor(@PathVariable Long idDoctor,
                                                    @Valid @RequestBody DoctorPatchDTO doctorPatchDTO){
        Doctors updatedDoctor= doctorsService.patchDoctor(idDoctor, doctorPatchDTO);
        return ResponseEntity.ok(updatedDoctor);

    }

    @DeleteMapping("/deleteDoctor/{idDoctor}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long idDoctor){
       doctorsService.removeDoctor(idDoctor);
       return ResponseEntity.ok("Doctor with id: " + idDoctor + "removed successfully");

    }

    @GetMapping("/findBySpecialty/{specialty}")
    public ResponseEntity<?> getDoctorBySpecialty(@PathVariable String specialty){
        List<Doctors> doctors= doctorsService.showBySpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/findByName/{firstName}")
    public ResponseEntity<?> getDoctorByName(@PathVariable String firstName){
        List<Doctors> doctors = doctorsService.showByName(firstName);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/findByLastName/{lastName}")
    public ResponseEntity<?> getDoctorByLastName(@PathVariable String lastName){
        List<Doctors> doctors = doctorsService.showByLastName(lastName);
        return ResponseEntity.ok(doctors);
    }




}
