package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.AppointmentEntity;
import com.example.appointment_schedule.entity.DayEntity;
import com.example.appointment_schedule.entity.TimeEntity;
import com.example.appointment_schedule.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<DayEntity> findDays(long doctor_id) {
        return appointmentRepository.findDays(doctor_id).orElse(null);
    }

    public List<TimeEntity> findTimes(long day_id) {
        return appointmentRepository.findTimes(day_id).orElse(null);
    }

//    public List<AppointmentEntity> findByDay(long id) {
//        return appointmentRepository.findByDay(id).orElse(null);
//    }

    public List<AppointmentEntity> findAll() {
        return appointmentRepository.findAll();
    }

    public AppointmentEntity findOne(long id) {
        Optional<AppointmentEntity> appointment = appointmentRepository.findById(id);
        return appointment.orElse(null);
    }

    @Transactional
    public void save(AppointmentEntity appointment) {
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void update(long id, AppointmentEntity appointment) {
        appointment.setId(id);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void delete(long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public void deleteByDay(long id) {
        appointmentRepository.deleteByDay(id);
    }
}
