package com.example.appointment_schedule.entity;

//import com.example.appointment_schedule.util.StartTimeBeforeEndTime;
import com.example.appointment_schedule.util.InTimeRange;
import com.example.appointment_schedule.util.StartTimeBeforeEndTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "doctor")
//@StartTimeBeforeEndTime(start = "start_time", end = "end_time")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    @NotEmpty(message = "Doctor's name shouldn't be empty")
    private String full_name;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "specialty")
    @NotEmpty(message = "Enter the specialty")
    private String specialty;

    @Column(name = "department")
    @NotEmpty(message = "Enter the department")
    private String department;

    @Column(name = "telephone")
    @Pattern(regexp = "^[\\+]?7[(]?[0-9]{3}[)]?[-\\s]?[0-9]{3}[-\\s]?[0-9]{2}[-\\s]?[0-9]{2}$",
            message = "Not valid. Example: +7(800)800-80-80")
    private String telephone;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DayEntity> days;

    @OneToMany(mappedBy = "doctor")
    private List<AppointmentEntity> appointments;

    @Column(name = "start_time")
    @InTimeRange
    private LocalTime start_time;

    @Column(name = "end_time")
    @InTimeRange
    private LocalTime end_time;
}
