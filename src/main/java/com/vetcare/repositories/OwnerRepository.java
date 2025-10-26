package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long>{

    List<Owner> findByActiveTrue();
    
    Optional<Owner> findByEmail(String email);
    
    Optional<Owner> findByDocumentNumber(String documentNumber);
    
    List<Owner> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName
    );
    
    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.pets WHERE o.id = :id")
    Optional<Owner> findByIdWithPets(Long id);

}
