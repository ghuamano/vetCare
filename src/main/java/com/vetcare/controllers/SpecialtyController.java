package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Specialty;
import com.vetcare.services.SpecialtyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@Valid @RequestBody Specialty specialty) {
        Specialty createdSpecialty = specialtyService.create(specialty);
        return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(
            @PathVariable Long id,
            @Valid @RequestBody Specialty specialty) {
        return ResponseEntity.ok(specialtyService.update(id, specialty));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
