package com.example.appointment_service.controller;

import com.example.appointment_service.dtos.AppointmentPatchDTO;
import com.example.appointment_service.dtos.AppointmentResponseDTO;
import com.example.appointment_service.models.Appointment;
import com.example.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/addAppointment")
    public ResponseEntity<?> addAppointment(@Valid @RequestBody Appointment appointment){
        try{
            Appointment newAppointment= appointmentService.createAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //ShowAllAppointments with all the information:
    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments(){
        List<Appointment> appointments= appointmentService.showAllAppointments();

        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> appointmentService.buildAppointmentResponse(appointment))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/getAppointmentByDoctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctor(doctorId);
        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> appointmentService.buildAppointmentResponse(appointment))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/getAppointmentByPatient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.findAppointmentsByPatient(patientId);
        List<AppointmentResponseDTO> responseList = appointments.stream()
                .map(appointment -> appointmentService.buildAppointmentResponse(appointment))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/changeAppointment/{idAppointment}")
    public ResponseEntity<Appointment> changeAppointment(@PathVariable Long idAppointment,
                                               @Valid @RequestBody AppointmentPatchDTO appointmentPatchDTO){
       Appointment appointment= appointmentService.updateAppointment(idAppointment, appointmentPatchDTO);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/getCompletedAppointment")
    public ResponseEntity<List<Appointment>> getAppointmentByStatus(){
        List<Appointment> appointmentList=appointmentService.findCompletedAppointments();
        return ResponseEntity.ok(appointmentList);
    }

}
