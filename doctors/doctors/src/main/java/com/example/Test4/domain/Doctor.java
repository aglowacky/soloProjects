package com.example.Test4.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String nip;
    private String specialization;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @ToString.Exclude
    private Set<Appointment> appointments;

    public Doctor(String firstName, String lastName, String nip, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nip = nip;
        this.specialization = specialization;
        this.appointments = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
