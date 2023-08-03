package com.example.appointment_schedule.services;

import com.example.appointment_schedule.entity.TimeEntity;
import com.example.appointment_schedule.repositories.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeEntity> findAllDayTimes(long id) {
        return timeRepository.findTimeEntitiesByDays(id);
    }


    public TimeEntity findOne(long id) {
        Optional<TimeEntity> time = timeRepository.findById(id);
        return time.orElse(null);
    }

    @Transactional
    public void save(TimeEntity time) {
        timeRepository.save(time);
    }

    @Transactional
    public void update(long id, TimeEntity time) {
        time.setId(id);
        timeRepository.save(time);
    }

    @Transactional
    public void delete(long id) {
        timeRepository.deleteById(id);
    }
}
