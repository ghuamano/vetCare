package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Clinic;
import com.vetcare.services.ClinicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clinics", description = "API for managing veterinary clinics")
public class ClinicController {

    private final ClinicService clinicService;

    @Operation(summary = "List all active clinics")
    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAllActive());
    }

    @Operation(summary = "Get clinic by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Clinic> getClinicById(
            @Parameter(description = "Clinic ID", required = true, example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(clinicService.findById(id));
    }

    @Operation(summary = "Find clinics by city")
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Clinic>> getClinicsByCity(
            @Parameter(description = "City name") @PathVariable String city) {
        return ResponseEntity.ok(clinicService.findByCity(city));
    }

    @Operation(summary = "Create new clinic")
    @PostMapping
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody Clinic clinic) {
        Clinic createdClinic = clinicService.create(clinic);
        return new ResponseEntity<>(createdClinic, HttpStatus.CREATED);
    }

    @Operation(summary = "Update clinic")
    @PutMapping("/{id}")
    public ResponseEntity<Clinic> updateClinic(
            @Parameter(description = "Clinic ID") @PathVariable Long id,
            @Valid @RequestBody Clinic clinic) {
        return ResponseEntity.ok(clinicService.update(id, clinic));
    }

    @Operation(summary = "Delete clinic")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(
            @Parameter(description = "Clinic ID") @PathVariable Long id) {
        clinicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate clinic")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateClinic(
            @Parameter(description = "Clinic ID") @PathVariable Long id) {
        clinicService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
