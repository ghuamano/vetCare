package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Owner;
import com.vetcare.services.OwnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners(){
        return ResponseEntity.ok(ownerService.findAllActive());
    } 

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @GetMapping("/{id}/pets")
    public ResponseEntity<Owner> getOwnerWithPets(@PathVariable Long id){
        return ResponseEntity.ok(ownerService.findByIdWithPets(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Owner>> searchOwners(@RequestParam String name){
        return ResponseEntity.ok(ownerService.searchByName(name));
    }
    
    @PostMapping
    public ResponseEntity<Owner> createOwner(@Validated @RequestBody Owner owner){
        Owner createdOwner = ownerService.create(owner);

        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @Valid @RequestBody Owner owner){
        return ResponseEntity.ok(ownerService.update(id, owner));
    }

    
}
