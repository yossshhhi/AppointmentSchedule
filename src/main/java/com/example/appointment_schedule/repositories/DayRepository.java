package com.example.appointment_schedule.repositories;

import com.example.appointment_schedule.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<DayEntity, Long> {
    @Query("SELECT MAX(d.date) FROM DayEntity d WHERE d.doctor.id = ?1")
    Date findMaxDateByDoctorId(long doctor_id);

    @Query("SELECT d FROM DayEntity d WHERE d.doctor.id = ?1")
    List<DayEntity> findDayEntitiesByDoctorId(long doctor_id);

}
