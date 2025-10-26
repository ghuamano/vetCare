package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>{

    List<Pet> findByActiveTrue();
    
    List<Pet> findByOwnerId(Long ownerId);
    
    List<Pet> findByPetTypeId(Long petTypeId);
    
    List<Pet> findByPrimaryClinicId(Long clinicId);
    
    List<Pet> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.visits WHERE p.id = :id")
    Optional<Pet> findByIdWithVisits(Long id);
    
    @Query("SELECT p FROM Pet p " +
        "JOIN FETCH p.owner " +
        "JOIN FETCH p.petType " +
        "WHERE p.id = :id")
    Optional<Pet> findByIdWithDetails(Long id);
}
