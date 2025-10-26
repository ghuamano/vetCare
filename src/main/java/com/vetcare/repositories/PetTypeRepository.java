package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vetcare.models.PetType;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long>{

    List<PetType> findByActiveTrue();

    Optional<PetType> findByName(String name);

    Boolean existsByName (String name);


}
