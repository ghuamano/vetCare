package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.PetType;
import com.vetcare.services.PetTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pet-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pet Types", description = "API for managing pet types")
public class PetTypeController {

    private final PetTypeService petTypeService;

    @Operation(summary = "List all pet types")
    @GetMapping
    public ResponseEntity<List<PetType>> getAllPetTypes() {
        return ResponseEntity.ok(petTypeService.findAllActive());
    }
    
    @Operation(summary = "Get pet type by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PetType> getPetTypeById(
            @Parameter(description = "Pet type ID") @PathVariable Long id) {
        return ResponseEntity.ok(petTypeService.findById(id));
    }
    
    @Operation(summary = "Create new pet type")
    @PostMapping
    public ResponseEntity<PetType> createPetType(@Valid @RequestBody PetType petType) {
        PetType createdPetType = petTypeService.create(petType);
        return new ResponseEntity<>(createdPetType, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update pet type")
    @PutMapping("/{id}")
    public ResponseEntity<PetType> updatePetType(
            @Parameter(description = "Pet type ID") @PathVariable Long id,
            @Valid @RequestBody PetType petType) {
        return ResponseEntity.ok(petTypeService.update(id, petType));
    }
    
    @Operation(summary = "Delete pet type")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(
            @Parameter(description = "Pet type ID") @PathVariable Long id) {
        petTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
