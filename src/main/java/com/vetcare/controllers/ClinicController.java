package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Clinic;
import com.vetcare.services.ClinicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAllActive());
    }

    @GetMapping("{id}")
    public ResponseEntity<Clinic> getClinicById(Long id){
        return ResponseEntity.ok(clinicService.findById(id));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Clinic>> getClinicsByCity (@PathVariable String city){
        return ResponseEntity.ok(clinicService.findByCity(city));
    }

    @PostMapping
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody Clinic clinic){
        Clinic createdClinic = clinicService.create(clinic);
        return new ResponseEntity<>(createdClinic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clinic> updateClinic(
            @PathVariable Long id ,
            @Valid @RequestBody Clinic clinic){
        return ResponseEntity.ok(clinicService.update(id, clinic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id){
        clinicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateClinic(@PathVariable Long id){
        clinicService.delete(id);
        return ResponseEntity.noContent().build();
    }





}
