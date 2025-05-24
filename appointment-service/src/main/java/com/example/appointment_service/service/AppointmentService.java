package com.example.appointment_service.service;


import com.example.appointment_service.dtos.AppointmentResponseDTO;
import com.example.appointment_service.dtos.DoctorDTO;
import com.example.appointment_service.dtos.PatientDTO;
import com.example.appointment_service.feignClient.DoctorClient;
import com.example.appointment_service.feignClient.PatientClient;
import com.example.appointment_service.models.Appointment;
import com.example.appointment_service.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
            throw new NoSuchElementException("No appointments found for doctor with ID: " + idDoctor);
        }
        return appointments;

    }

    //get an appointment by patient:
    public List<Appointment> findAppointmentsByPatient(Long idPatient){
        List<Appointment> appointments = appointmentRepository.findByDoctor(idPatient);
        if (appointments.isEmpty()) {
            throw new NoSuchElementException("No appointments found for doctor with ID: " + idPatient);
        }
        return appointments;

    }


}
