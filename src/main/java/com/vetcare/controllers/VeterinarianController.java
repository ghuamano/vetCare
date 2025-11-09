package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Veterinarian;
import com.vetcare.services.VeterinarianService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Veterinarians", description = "API for managing veterinarians")
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

    @Operation(summary = "Get all active veterinarians")
    @ApiResponse(responseCode = "200", description = "List of active veterinarians retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Veterinarian>> getAllVeterinarians(){
        return ResponseEntity.ok(veterinarianService.findAllActive());
    }


    @Operation(summary = "Get veterinarian by ID")
    @ApiResponse(responseCode = "200", description = "Veterinarian retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @GetMapping("/{id}")
    public ResponseEntity<Veterinarian> getVeterinarianById(@Parameter(description = "Veterinarian ID") @PathVariable Long id){
        return ResponseEntity.ok(veterinarianService.findByIdWithSpecialties(id));
    }

    @Operation(summary = "Get veterinarians by specialty")
    @ApiResponse(responseCode = "200", description = "Veterinarians retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Specialty not found")
    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<Veterinarian>> getVeterinarianBySpecialty(@Parameter(description = "Specialty ID") @PathVariable Long specialtyId){
        return ResponseEntity.ok(veterinarianService.findBySpecialty(specialtyId));
    }

    @Operation(summary = "Create new veterinarian")
    @ApiResponse(responseCode = "201", description = "Veterinarian created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<Veterinarian> createVeterinarian(@Valid @RequestBody Veterinarian veterinarian){
        Veterinarian createdVet = veterinarianService.create(veterinarian);
        return new ResponseEntity<>(createdVet, HttpStatus.CREATED);
    }

    @Operation(summary = "Add specialty to veterinarian")
    @ApiResponse(responseCode = "200", description = "Specialty added successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian or Specialty not found")
    @PostMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<Veterinarian> addSpecialty( @Parameter(description = "Veterinarian ID") @PathVariable Long veterinarianId, @Parameter(description = "Specialty ID") @PathVariable Long specialtyId){
        return ResponseEntity.ok(veterinarianService.addSpecialty(veterinarianId, specialtyId));
    }

    @Operation(summary = "Update veterinarian")
    @ApiResponse(responseCode = "200", description = "Veterinarian updated successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @PutMapping("/{id}")
    public ResponseEntity<Veterinarian> updateVeterinarian(
            @Parameter(description = "Veterinarian ID")
            @PathVariable Long id, 
            @Valid @RequestBody Veterinarian veterinarian){
        return ResponseEntity.ok(veterinarianService.update(id, veterinarian));
    }

    @Operation(summary = "Remove specialty from veterinarian")
    @ApiResponse(responseCode = "200", description = "Specialty removed successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian or Specialty not found")
    @DeleteMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<Veterinarian> removeSpecialty(
            @Parameter(description = "Veterinarian ID") @PathVariable Long veterinarianId,
            @Parameter(description = "Specialty ID") @PathVariable Long specialtyId) {
        return ResponseEntity.ok(veterinarianService.removeSpecialty(veterinarianId, specialtyId));
    }

    @Operation(summary = "Delete veterinarian")
    @ApiResponse(responseCode = "204", description = "Veterinarian deleted successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian(@Parameter(description = "Veterinarian ID") @PathVariable Long id) {
        veterinarianService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Deactivate veterinarian")
    @ApiResponse(responseCode = "204", description = "Veterinarian deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVeterinarian(@Parameter(description = "Veterinarian ID") @PathVariable Long id) {
        veterinarianService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
