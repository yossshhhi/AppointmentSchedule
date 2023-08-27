package com.example.appointment_schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "client")
@Data
public class ClientEntity {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Client's name don't be empty")
    @Size(max = 100, message = "Name is too long")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

//    @OneToOne(mappedBy = "client")
//    private Set<AppointmentEntity> appointment;
}
