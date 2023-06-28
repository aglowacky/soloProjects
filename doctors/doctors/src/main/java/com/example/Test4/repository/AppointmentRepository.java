package com.example.Test4.repository;

import com.example.Test4.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Appointment> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Appointment> findById(Long id);
}
