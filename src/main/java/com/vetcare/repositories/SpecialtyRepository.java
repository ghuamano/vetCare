package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long>{

    List<Specialty> findByActiveTrue();

    Optional<Specialty> findByName(String name);

    boolean existsByName(String name);

}
