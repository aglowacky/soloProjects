package com.example.Test4.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctorId", nullable = false)
    @ToString.Exclude
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    @ToString.Exclude
    private Patient patient;
    private LocalDateTime dateTime;
    private int timeInMinutes;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime dateTime, int timeInMinutes) {
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.timeInMinutes = timeInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Appointment that = (Appointment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
