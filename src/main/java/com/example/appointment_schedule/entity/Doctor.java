package com.example.appointment_schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {
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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Day> days;
}
