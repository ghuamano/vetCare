package com.vetcare.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Visit;
import com.vetcare.services.VisitService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits(){
        return ResponseEntity.ok(visitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable Long id){
        return ResponseEntity.ok(visitService.findByIdWithDetails(id));
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<Visit>> getVisitByPet(@PathVariable Long petId){
        return ResponseEntity.ok(visitService.findByPetId(petId));
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<Visit>> getVisitByVeterinarian(@PathVariable Long veterinarianId){
        return ResponseEntity.ok(visitService.findByVeterinarianId(veterinarianId));
    }

    @GetMapping("/data-range")
    public ResponseEntity<List<Visit>> getVisitByDataRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        return ResponseEntity.ok(visitService.findByDateRange(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(
            @Valid @RequestBody Visit visit,
            @RequestParam Long petId,
            @RequestParam Long veterinarianId,
            @RequestParam Long clinicId) {
        Visit createdVisit = visitService.create(visit, petId, veterinarianId, clinicId);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(
            @PathVariable Long id,
            @Valid @RequestBody Visit visit) {
        return ResponseEntity.ok(visitService.update(id, visit));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Visit> updateVisitStatus(
            @PathVariable Long id,
            @RequestParam Visit.VisitStatus status) {
        return ResponseEntity.ok(visitService.updateStatus(id, status));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.delete(id);
        return ResponseEntity.noContent().build();
    }
        
}
