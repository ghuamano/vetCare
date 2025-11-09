package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Pet;
import com.vetcare.services.PetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pets", description = "API for managing pets")
public class PetController {

    private final PetService petService;

    @Operation(summary = "List all active pets",
            description = "Retrieves a list of all active pets in the system")
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.findAllActive());
    }

    @Operation(summary = "Get pet by ID", description = "Retrieves full details of a pet including owner and type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(
            @Parameter(description = "Pet ID") @PathVariable Long id) {
        return ResponseEntity.ok(petService.findByIdWithDetails(id));
    }

    @Operation(summary = "List pets for an owner", description = "Retrieves all pets registered to a specific owner")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Pet>> getPetsByOwner(
            @Parameter(description = "Owner ID") @PathVariable Long ownerId) {
        return ResponseEntity.ok(petService.findByOwnerId(ownerId));
    }

    @Operation(summary = "Search pets by name")
    @GetMapping("/search")
    public ResponseEntity<List<Pet>> searchPets(
            @Parameter(description = "Pet name") @RequestParam String name) {
        return ResponseEntity.ok(petService.searchByName(name));
    }

    @Operation(summary = "Register a new pet", description = "Registers a new pet in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Owner or pet type not found")
    })
    @PostMapping
    public ResponseEntity<Pet> createPet(
            @Valid @RequestBody Pet pet,
            @Parameter(description = "Owner ID") @RequestParam Long ownerId,
            @Parameter(description = "Pet type ID") @RequestParam Long petTypeId) {
        Pet createdPet = petService.create(pet, ownerId, petTypeId);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @Operation(summary = "Update pet")
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(
            @Parameter(description = "Pet ID") @PathVariable Long id,
            @Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.update(id, pet));
    }

    @Operation(summary = "Delete pet")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @Parameter(description = "Pet ID") @PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate pet")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePet(
            @Parameter(description = "Pet ID") @PathVariable Long id) {
        petService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
