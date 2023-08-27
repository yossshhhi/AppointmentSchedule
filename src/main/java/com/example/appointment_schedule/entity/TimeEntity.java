package com.example.appointment_schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "time")
public class TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Temporal(TemporalType.TIME)
    @Column(name = "appointment")
    private LocalTime appointment;

//    @ManyToMany(mappedBy = "time")
//    private Set<DayEntity> days;

    @OneToMany(mappedBy = "time")
    private Set<AppointmentEntity> appointments;
}
