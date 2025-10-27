package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    List<Clinic> findByActiveTrue();

    List<Clinic> findByCity(String city);

    List<Clinic> findByNameContainingIgnoreCase(String name);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    @Query("SELECT c FROM Clinic c LEFT JOIN FETCH c.veterinarians WHERE c.id = :id")
    Optional<Clinic> findByIdWithVeterinarians(Long id);

}
