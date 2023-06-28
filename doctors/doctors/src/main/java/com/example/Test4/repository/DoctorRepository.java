package com.example.Test4.repository;

import com.example.Test4.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Doctor> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByNip(String nip);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Doctor> findById(Long id);
}
