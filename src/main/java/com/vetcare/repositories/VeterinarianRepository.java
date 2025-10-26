package com.vetcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>{

    List<Veterinarian> findByActiveTrue();

    Optional<Veterinarian> findByEmail(String email);

    Optional<Veterinarian> findByLicenseNumber(String licenseNumber);

    List<Veterinarian> findByClinicId(Long clinicId);

    @Query("SELECT v FROM Veterinarian v " +
        "JOIN v.specialties s " +
        "WHERE s.id = :specialtyId AND v.active = true")
    List<Veterinarian> findBySpecialtyId(Long specialtyId);
    
    @Query("SELECT v FROM Veterinarian v " +
        "LEFT JOIN FETCH v.specialties " +
        "WHERE v.id = :id")
    Optional<Veterinarian> findByIdWithSpecialties(Long id);
    
    List<Veterinarian> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName
    );


}
