package com.example.appointment_schedule.repositories;

import com.example.appointment_schedule.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    @Query("SELECT MAX(d.date) FROM Day d WHERE d.doctor.id = ?1")
    Date findMaxDateByDoctorId(long doctor_id);
}
