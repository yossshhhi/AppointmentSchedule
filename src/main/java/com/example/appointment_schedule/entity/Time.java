package com.example.appointment_schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^(1[0-9]):[0-5][0-9]$", message = "It's non-working time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "day_id", referencedColumnName = "id")
    private Day day;

    @Transient
    private List<String> allWorkTimes;
}
