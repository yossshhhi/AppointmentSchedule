package com.example.appointment_schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "appointment")
@Data
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "day_id", referencedColumnName = "id")
    private DayEntity day;

    @ManyToOne
    @JoinColumn(name = "time_id", referencedColumnName = "id")
    private TimeEntity time;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorEntity doctor;

//    @OneToOne
//    @JoinColumn(name = "client_id", referencedColumnName = "id")
//    private ClientEntity client;

    public AppointmentEntity(TimeEntity time, DayEntity day, DoctorEntity doctor) {
        this.day = day;
        this.time = time;
        this.doctor = doctor;
    }

    public AppointmentEntity() {}
}
