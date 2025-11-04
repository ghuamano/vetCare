package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.PetType;
import com.vetcare.services.PetTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pet-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetTypeController {

    private final PetTypeService petTypeService;

    @GetMapping
    public ResponseEntity<List<PetType>> getAllPetTypes() {
        return ResponseEntity.ok(petTypeService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetType> getPetTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(petTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PetType> createPetType(@Valid @RequestBody PetType petType) {
        PetType createdPetType = petTypeService.create(petType);
        return new ResponseEntity<>(createdPetType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetType> updatePetType(
            @PathVariable Long id,
            @Valid @RequestBody PetType petType) {
        return ResponseEntity.ok(petTypeService.update(id, petType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(Long id){
        petTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
