package com.example.doctor_service.service;

import com.example.doctor_service.client.PatientClient;
import com.example.doctor_service.dtos.DoctorPatchDTO;
import com.example.doctor_service.dtos.PatientDTO;
import com.example.doctor_service.models.Doctors;
import com.example.doctor_service.repository.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class DoctorsService {
    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private PatientClient patientClient;

    public List<Doctors> createDoctor(List<Doctors> doctors){
        return doctorsRepository.saveAll(doctors);
    }

    public List<Doctors> showAllDoctors(){
        return doctorsRepository.findAll();
    }

    public Doctors showDoctorById(Long idDoctor) {
        return doctorsRepository.findById(idDoctor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));
    }


    public Doctors updateDoctor(Long idDoctor, Doctors doctors){
        Doctors updatedDoctor = showDoctorById(idDoctor);
        updatedDoctor.setFirstName(doctors.getFirstName());
        updatedDoctor.setLastName(doctors.getLastName());
        updatedDoctor.setEmail(doctors.getEmail());
        updatedDoctor.setAvailableDays(doctors.getAvailableDays());
        updatedDoctor.setLicenceNumber(doctors.getLicenceNumber());
        updatedDoctor.setPhoneNumber(doctors.getPhoneNumber());

        return doctorsRepository.save(updatedDoctor);
    }

    public Doctors patchDoctor(Long idDoctor, DoctorPatchDTO doctorPatchDTO){
        Doctors doctor= showDoctorById(idDoctor);
        if (doctorPatchDTO.getFirstName() != null) {
            doctor.setFirstName(doctorPatchDTO.getFirstName());
        }
        if (doctorPatchDTO.getLastName() != null) {
            doctor.setLastName(doctorPatchDTO.getLastName());
        }
        if (doctorPatchDTO.getEmail() != null) {
            doctor.setEmail(doctorPatchDTO.getEmail());
        }
        if (doctorPatchDTO.getAvailableDays() != null) {
            doctor.setAvailableDays(doctorPatchDTO.getAvailableDays());
        }
        if (doctorPatchDTO.getLicenceNumber() != null) {
            doctor.setLicenceNumber(doctorPatchDTO.getLicenceNumber());
        }
        if (doctorPatchDTO.getPhoneNumber() != null) {
            doctor.setPhoneNumber(doctorPatchDTO.getPhoneNumber());
        }


        return doctorsRepository.save(doctor);
    }

    public void removeDoctor(Long idDoctor){
        Doctors doctor= showDoctorById(idDoctor);
        doctorsRepository.delete(doctor);
    }

    public List<Doctors> showBySpecialty(String specialty){
        List<Doctors> doctorsList= doctorsRepository.findBySpecialty(specialty);
        if (doctorsList.isEmpty()){
            throw new NoSuchElementException("No doctors where found under this specialty " + specialty );
        }
        return doctorsList;
    }

    public List<Doctors> showByName(String firstName) {
        List<Doctors> doctors = doctorsRepository.findByFirstName(firstName);
        if (doctors.isEmpty()) {
            throw new RuntimeException("No doctors found with firstName: " + firstName);
        }
        return doctors;
    }


    public List<Doctors> showByLastName(String lastName){
        List<Doctors> doctors = doctorsRepository.findByLastName(lastName);
        if (doctors.isEmpty()) {
            throw new RuntimeException("No doctors found with lastName: " + lastName);
        }
        return doctors;
    }

    public List<PatientDTO> showPatients(Long idDoctor){
        List<PatientDTO> patientDTOS = patientClient.getPatientsByDoctorId(idDoctor);
        return patientDTOS;

    }

}
