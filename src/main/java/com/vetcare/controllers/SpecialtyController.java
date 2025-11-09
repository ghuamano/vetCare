package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Specialty;
import com.vetcare.services.SpecialtyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Specialties", description = "API for managing veterinary specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @Operation(summary = "List all specialties")
    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAllActive());
    }
    
    @Operation(summary = "Get specialty by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(
            @Parameter(description = "ID of the specialty") @PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }
    
    @Operation(summary = "Create new specialty")
    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@Valid @RequestBody Specialty specialty) {
        Specialty createdSpecialty = specialtyService.create(specialty);
        return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update specialty")
    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(
            @Parameter(description = "ID of the specialty") @PathVariable Long id,
            @Valid @RequestBody Specialty specialty) {
        return ResponseEntity.ok(specialtyService.update(id, specialty));
    }
    
    @Operation(summary = "Delete specialty")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(
            @Parameter(description = "ID of the specialty") @PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
