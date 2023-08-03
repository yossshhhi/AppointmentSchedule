package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.DoctorEntity;
import com.example.appointment_schedule.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorEntity> findAll() {
        return doctorRepository.findAll();
    }

    public DoctorEntity findOne(long id) {
        Optional<DoctorEntity> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    @Transactional
    public void save(DoctorEntity doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    public void update(long id, DoctorEntity doctor) {
        doctor.setId(id);
        doctorRepository.save(doctor);
    }

    @Transactional
    public void delete(long id) {
        doctorRepository.deleteById(id);
    }
}
