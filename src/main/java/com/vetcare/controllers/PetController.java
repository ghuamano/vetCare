package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.vetcare.models.Pet;
import com.vetcare.services.PetService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets(){
        return ResponseEntity.ok(petService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id){
        return ResponseEntity.ok(petService.findByIdWithDetails(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable Long ownerId){
        return ResponseEntity.ok(petService.findByOwnerId(ownerId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pet>> searchByName(@RequestParam String name){
        return ResponseEntity.ok(petService.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Validated @RequestBody Pet pet, @RequestParam Long ownerId, @RequestParam Long typeId){
        Pet createdPet = petService.create(pet, ownerId, typeId);
    
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.update(id, pet));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePet(@PathVariable Long id) {
        petService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
