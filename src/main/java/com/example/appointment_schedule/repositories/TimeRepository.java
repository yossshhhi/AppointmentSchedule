package com.example.appointment_schedule.repositories;

import com.example.appointment_schedule.entity.TimeEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Long> {
    @Query("SELECT t FROM TimeEntity t WHERE t.appointment >= ?1 AND t.appointment <= ?2")
    List<TimeEntity> findTimeListByTime(@PastOrPresent LocalTime start, @FutureOrPresent LocalTime end);

    @Query("SELECT t FROM TimeEntity t JOIN t.days d WHERE d.id = ?1")
    List<TimeEntity> findTimeEntitiesByDays(long day_id);
}
