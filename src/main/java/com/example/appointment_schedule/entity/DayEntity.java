package com.example.appointment_schedule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "day")
public class DayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorEntity doctor;

    @OneToMany(mappedBy = "day")
    private List<AppointmentEntity> appointments;

    @Column(name = "is_work_day")
    private boolean isWorkDay;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "day_time",
//            joinColumns = {@JoinColumn(name = "day_id")},
//            inverseJoinColumns = {@JoinColumn(name = "time_id")}
//    )
//    private List<TimeEntity> time;

    public DayEntity(Date date, DoctorEntity doctor, boolean isWorkDay) {
        this.date = date;
        this.doctor = doctor;
        this.isWorkDay = isWorkDay;
    }

    public DayEntity() {

    }
}
