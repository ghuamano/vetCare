package com.vetcare.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Visit;
import com.vetcare.services.VisitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Visits", description = "API for managing visits")
public class VisitController {

    private final VisitService visitService;

    @Operation(summary = "Get all visits")
    @ApiResponse(responseCode = "200", description = "List of visits retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits(){
        return ResponseEntity.ok(visitService.findAll());
    }

    @Operation(summary = "Get visit by ID")
    @ApiResponse(responseCode = "200", description = "Visit retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Visit not found")
    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById( @Parameter(description = "Visit ID") @PathVariable Long id){
        return ResponseEntity.ok(visitService.findByIdWithDetails(id));
    }

    @Operation(summary = "Get visits by pet ID")
    @ApiResponse(responseCode = "200", description = "Visits retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Pet not found")
    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<Visit>> getVisitByPet(@Parameter(description = "Pet ID") @PathVariable Long petId){
        return ResponseEntity.ok(visitService.findByPetId(petId));
    }

    @Operation(summary = "Get visits by veterinarian ID")
    @ApiResponse(responseCode = "200", description = "Visits retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<Visit>> getVisitByVeterinarian(@Parameter(description = "Veterinarian ID") @PathVariable Long veterinarianId){
        return ResponseEntity.ok(visitService.findByVeterinarianId(veterinarianId));
    }

    @Operation(summary = "Get visits by date range")
    @ApiResponse(responseCode = "200", description = "Visits retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No visits found in the given date range")
    @GetMapping("/date-range")
    public ResponseEntity<List<Visit>> getVisitByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        return ResponseEntity.ok(visitService.findByDateRange(startDate, endDate));
    }

    @Operation(summary = "Create new visit")
    @ApiResponse(responseCode = "201", description = "Visit created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<Visit> createVisit(
            @Valid @RequestBody Visit visit,
            @RequestParam Long petId,
            @RequestParam Long veterinarianId,
            @RequestParam Long clinicId) {
        Visit createdVisit = visitService.create(visit, petId, veterinarianId, clinicId);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update visit")
    @ApiResponse(responseCode = "200", description = "Visit updated successfully")
    @ApiResponse(responseCode = "404", description = "Visit not found")
    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(
            @Parameter(description = "Visit ID") @PathVariable Long id,
            @Valid @RequestBody Visit visit) {
        return ResponseEntity.ok(visitService.update(id, visit));
    }
    
    @Operation(summary = "Update visit status")
    @ApiResponse(responseCode = "200", description = "Visit status updated successfully")
    @ApiResponse(responseCode = "404", description = "Visit not found")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Visit> updateVisitStatus(
            @Parameter(description = "Visit ID") @PathVariable Long id,
            @RequestParam Visit.VisitStatus status) {
        return ResponseEntity.ok(visitService.updateStatus(id, status));
    }
    
    @Operation(summary = "Delete visit")
    @ApiResponse(responseCode = "204", description = "Visit deleted successfully")
    @ApiResponse(responseCode = "404", description = "Visit not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@Parameter(description = "Visit ID") @PathVariable Long id) {
        visitService.delete(id);
        return ResponseEntity.noContent().build();
    }
        
}
