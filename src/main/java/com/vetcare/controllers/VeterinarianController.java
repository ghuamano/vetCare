package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Veterinarian;
import com.vetcare.services.VeterinarianService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

    @GetMapping
    public ResponseEntity<List<Veterinarian>> getAllVeterinarians(){
        return ResponseEntity.ok(veterinarianService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinarian> getVeterinarianById(@PathVariable Long id){
        return ResponseEntity.ok(veterinarianService.findByIdWithSpecialties(id));
    }

    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<Veterinarian>> getVeterinarianBySpecialty(@PathVariable Long specialtyId){
        return ResponseEntity.ok(veterinarianService.findBySpecialty(specialtyId));
    }

    @PostMapping
    public ResponseEntity<Veterinarian> createVeterinarian(@Valid @RequestBody Veterinarian veterinarian){
        Veterinarian createdVet = veterinarianService.create(veterinarian);
        return new ResponseEntity<>(createdVet, HttpStatus.CREATED);
    }

    @PostMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<Veterinarian> addSpecialty(@PathVariable Long veterinarianId, @PathVariable Long specialtyId){
        return ResponseEntity.ok(veterinarianService.addSpecialty(veterinarianId, specialtyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinarian> updateVeterinarian(
            @PathVariable Long id, 
            @Valid @RequestBody Veterinarian veterinarian){
        return ResponseEntity.ok(veterinarianService.update(id, veterinarian));
    }

    @DeleteMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<Veterinarian> removeSpecialty(
            @PathVariable Long veterinarianId,
            @PathVariable Long specialtyId) {
        return ResponseEntity.ok(veterinarianService.removeSpecialty(veterinarianId, specialtyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian(@PathVariable Long id) {
        veterinarianService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVeterinarian(@PathVariable Long id) {
        veterinarianService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
