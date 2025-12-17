package com.vetcare.controllers;

import com.vetcare.dto.*;
import com.vetcare.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pets", description = "Operations related to pet management")
public class PetController {
    
    private final PetService petService;
    
    @Operation(
        summary = "Get all active pets",
        description = "Retrieves a list of all active pets registered in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pets",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAllActive());
    }
    
    @Operation(
        summary = "Get pet by ID",
        description = "Retrieves detailed information about a specific pet including owner and type"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pet found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(
            @Parameter(description = "ID of the pet to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }
    
    @Operation(
        summary = "Get pets by owner",
        description = "Retrieves all pets registered for a specific owner"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved owner's pets")
    })
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(
            @Parameter(description = "ID of the owner", required = true, example = "1")
            @PathVariable Long ownerId) {
        return ResponseEntity.ok(petService.findByOwnerId(ownerId));
    }
    
    @Operation(
        summary = "Search pets by name",
        description = "Searches for pets by name (partial match)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<List<PetDTO>> searchPets(
            @Parameter(description = "Pet name to search for", required = true, example = "Max")
            @RequestParam String name) {
        return ResponseEntity.ok(petService.searchByName(name));
    }
    
    @Operation(
        summary = "Register new pet",
        description = "Registers a new pet in the system with owner and type association"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pet created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Owner or pet type not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PetDTO> createPet(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Pet object to be created (includes ownerId and petTypeId)",
                required = true,
                content = @Content(schema = @Schema(implementation = CreatePetDTO.class))
            )
            @Valid @RequestBody CreatePetDTO createPetDTO) {
        PetDTO createdPet = petService.create(createPetDTO);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Update pet",
        description = "Updates the information of an existing pet"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pet updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(
            @Parameter(description = "ID of the pet to update", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated pet information",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdatePetDTO.class))
            )
            @Valid @RequestBody UpdatePetDTO updatePetDTO) {
        return ResponseEntity.ok(petService.update(id, updatePetDTO));
    }
    
    @Operation(
        summary = "Delete pet",
        description = "Permanently deletes a pet from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @Parameter(description = "ID of the pet to delete", required = true, example = "1")
            @PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
        summary = "Deactivate pet",
        description = "Soft deletes a pet by marking it as inactive"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pet deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePet(
            @Parameter(description = "ID of the pet to deactivate", required = true, example = "1")
            @PathVariable Long id) {
        petService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}