package com.example.appointment_schedule.repositories;

import com.example.appointment_schedule.entity.AppointmentEntity;
import com.example.appointment_schedule.entity.DayEntity;
import com.example.appointment_schedule.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository  extends JpaRepository<AppointmentEntity, Long> {
    @Query("select a.day from AppointmentEntity a where a.doctor.id = ?1")
    Optional<List<DayEntity>> findDays(long doctor_id);

    @Query("select a.time from AppointmentEntity a where a.day.id = ?1")
    Optional<List<TimeEntity>> findTimes(long day_id);

    @Query("delete from AppointmentEntity a where a.day.id = ?1")
    void deleteByDay(long day_id);
}
