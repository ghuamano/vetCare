package com.vetcare.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vetcare.models.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByPetId(Long petId);

    List<Visit> findByVeterinarianId(Long veterinarianId);

    List<Visit> findByClinicId(Long clinicId);

    List<Visit> findByStatus(Visit.VisitStatus status);

    @Query("SELECT v FROM Visit v WHERE v.visitDate BETWEEN :startDate AND :endDate")
    List<Visit> findByVisitDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.pet.owner.id = :ownerId " +
            "ORDER BY v.visitDate DESC")
    List<Visit> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT v FROM Visit v " +
            "JOIN FETCH v.pet p " +
            "JOIN FETCH p.owner " +
            "JOIN FETCH v.veterinarian " +
            "JOIN FETCH v.clinic " +
            "WHERE v.id = :id")
    Optional<Visit> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.veterinarian.id = :vetId " +
            "AND v.visitDate BETWEEN :startDate AND :endDate " +
            "AND v.status = :status")
    List<Visit> findVeterinarianSchedule(
            @Param("vetId") Long vetId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") Visit.VisitStatus status);
}
