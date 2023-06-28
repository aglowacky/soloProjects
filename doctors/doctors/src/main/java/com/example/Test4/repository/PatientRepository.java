package com.example.Test4.repository;

import com.example.Test4.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Patient> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByPesel(String pesel);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Patient> findById(@Param("id") Long id);
}
