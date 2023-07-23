package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.Day;
import com.example.appointment_schedule.entity.Time;
import com.example.appointment_schedule.repositories.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findWorkTime() {
        return timeRepository.findAll();
    }



    public Time findOne(long id) {
        Optional<Time> time = timeRepository.findById(id);
        return time.orElse(null);
    }

    @Transactional
    public void save(Time time) {
        timeRepository.save(time);
    }

    @Transactional
    public void update(long id, Time time) {
        time.setId(id);
        timeRepository.save(time);
    }

    @Transactional
    public void delete(long id) {
        timeRepository.deleteById(id);
    }
}
