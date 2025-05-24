package com.example.appointment_service.service;


import com.example.appointment_service.dtos.AppointmentPatchDTO;
import com.example.appointment_service.dtos.AppointmentResponseDTO;
import com.example.appointment_service.dtos.DoctorDTO;
import com.example.appointment_service.dtos.PatientDTO;
import com.example.appointment_service.enums.AppointmentStatus;
import com.example.appointment_service.feignClient.DoctorClient;
import com.example.appointment_service.feignClient.PatientClient;
import com.example.appointment_service.models.Appointment;
import com.example.appointment_service.repository.AppointmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorClient doctorClient;
    @Autowired
    private PatientClient patientClient;
    //Add appointment
    public Appointment createAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
    //show all appointments:
    public List<Appointment> showAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment findAppointmentById(Long idAppointment){
        return appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
    }


    @CircuitBreaker(name = "doctor-service", fallbackMethod = "fallbackDoctor")
    public DoctorDTO getDoctorById(Long idDoctor) {
        return doctorClient.getDoctorById(idDoctor);
    }

    public DoctorDTO fallbackDoctor(Long idDoctor, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Doctor service not available");
    }

    @CircuitBreaker(name = "patient-service", fallbackMethod = "fallbackPatient")
    public PatientDTO getPatientById(Long idPatient) {
        return patientClient.getPatientById(idPatient);
    }

    public PatientDTO fallbackPatient(Long idPatient, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Patient service not available");
    }

    //bodyResponse which shows all the information about appointment, doctor and patient:
    public AppointmentResponseDTO buildAppointmentResponse(Appointment appointment){
        DoctorDTO doctor= doctorClient.getDoctorById(appointment.getDoctorId());
        PatientDTO patient= patientClient.getPatientById(appointment.getPatientId());

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setReason(appointment.getReason());
        response.setPatient(patient);
        response.setDoctor(doctor);

        return response;
    }

    //get an appointment by doctor:
    public List<Appointment> findAppointmentsByDoctor(Long idDoctor){
        List<Appointment> appointments = appointmentRepository.findByDoctor(idDoctor);
        if (appointments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found for doctor with ID: " + idDoctor);
        }
        return appointments;

    }

    //get an appointment by patient:
    public List<Appointment> findAppointmentsByPatient(Long idPatient){
        List<Appointment> appointments = appointmentRepository.findByPatient(idPatient);
        if (appointments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found for patient with ID: " + idPatient);
        }
        return appointments;

    }
    @Transactional
    public Appointment updateAppointment(Long idAppointment, AppointmentPatchDTO appointmentPatchDTO){
        Appointment appointment = findAppointmentById(idAppointment);
        if (appointmentPatchDTO.getAppointmentDate() != null){
            appointment.setAppointmentDate(appointmentPatchDTO.getAppointmentDate());
        }
        if (appointmentPatchDTO.getAppointmentTime() != null){
            appointment.setAppointmentTime(appointmentPatchDTO.getAppointmentTime());
        }
        if (appointmentPatchDTO.getReason() != null){
            appointment.setReason(appointmentPatchDTO.getReason());
        }
        if (appointmentPatchDTO.getStatus() != null) {
            appointment.setStatus(appointmentPatchDTO.getStatus());
        }
        if (appointmentPatchDTO.getDoctorId()!= null){
            appointment.setDoctorId(appointmentPatchDTO.getDoctorId());
        }
        if (appointmentPatchDTO.getPatientId()!= null){
            appointment.setPatientId(appointmentPatchDTO.getPatientId());
        }

        return appointmentRepository.save(appointment);

    }

    public List<Appointment> findCompletedAppointments() {
        return appointmentRepository.findCompletedAppointments(AppointmentStatus.COMPLETED);
    }


}
