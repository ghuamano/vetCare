package com.vetcare.controllers;

import com.vetcare.dto.*;
import com.vetcare.services.VeterinarianService;
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
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Veterinarians", description = "Operations related to veterinarian staff management")
public class VeterinarianController {
    
    private final VeterinarianService veterinarianService;
    
    @Operation(
        summary = "Get all active veterinarians",
        description = "Retrieves a list of all active veterinarians in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of veterinarians",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<VeterinarianDTO>> getAllVeterinarians() {
        return ResponseEntity.ok(veterinarianService.findAllActive());
    }
    
    @Operation(
        summary = "Get veterinarian by ID",
        description = "Retrieves detailed information about a specific veterinarian including their specialties"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veterinarian found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veterinarian not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianDTO> getVeterinarianById(
            @Parameter(description = "ID of the veterinarian to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(veterinarianService.findById(id));
    }
    
    @Operation(
        summary = "Get veterinarians by specialty",
        description = "Retrieves all veterinarians who have a specific medical specialty"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved veterinarians with the specialty")
    })
    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<VeterinarianDTO>> getVeterinariansBySpecialty(
            @Parameter(description = "ID of the specialty", required = true, example = "1")
            @PathVariable Long specialtyId) {
        return ResponseEntity.ok(veterinarianService.findBySpecialty(specialtyId));
    }
    
    @Operation(
        summary = "Create new veterinarian",
        description = "Registers a new veterinarian in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veterinarian created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Email or license number already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VeterinarianDTO> createVeterinarian(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Veterinarian object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = CreateVeterinarianDTO.class))
            )
            @Valid @RequestBody CreateVeterinarianDTO createVeterinarianDTO) {
        VeterinarianDTO createdVet = veterinarianService.create(createVeterinarianDTO);
        return new ResponseEntity<>(createdVet, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Add specialty to veterinarian",
        description = "Associates a medical specialty with a veterinarian"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Specialty added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veterinarian or specialty not found", content = @Content)
    })
    @PostMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<VeterinarianDTO> addSpecialty(
            @Parameter(description = "ID of the veterinarian", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Parameter(description = "ID of the specialty to add", required = true, example = "2")
            @PathVariable Long specialtyId) {
        return ResponseEntity.ok(veterinarianService.addSpecialty(veterinarianId, specialtyId));
    }
    
    @Operation(
        summary = "Remove specialty from veterinarian",
        description = "Removes a medical specialty association from a veterinarian"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Specialty removed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veterinarian or specialty not found", content = @Content)
    })
    @DeleteMapping("/{veterinarianId}/specialties/{specialtyId}")
    public ResponseEntity<VeterinarianDTO> removeSpecialty(
            @Parameter(description = "ID of the veterinarian", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Parameter(description = "ID of the specialty to remove", required = true, example = "2")
            @PathVariable Long specialtyId) {
        return ResponseEntity.ok(veterinarianService.removeSpecialty(veterinarianId, specialtyId));
    }
    
    @Operation(
        summary = "Update veterinarian",
        description = "Updates the information of an existing veterinarian"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veterinarian updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VeterinarianDTO.class))),
        @ApiResponse(responseCode = "404", description = "Veterinarian not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Email already in use by another veterinarian", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarianDTO> updateVeterinarian(
            @Parameter(description = "ID of the veterinarian to update", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated veterinarian information",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdateVeterinarianDTO.class))
            )
            @Valid @RequestBody UpdateVeterinarianDTO updateVeterinarianDTO) {
        return ResponseEntity.ok(veterinarianService.update(id, updateVeterinarianDTO));
    }
    
    @Operation(
        summary = "Delete veterinarian",
        description = "Permanently deletes a veterinarian from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veterinarian deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Veterinarian not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian(
            @Parameter(description = "ID of the veterinarian to delete", required = true, example = "1")
            @PathVariable Long id) {
        veterinarianService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
        summary = "Deactivate veterinarian",
        description = "Soft deletes a veterinarian by marking them as inactive"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veterinarian deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Veterinarian not found", content = @Content)
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVeterinarian(
            @Parameter(description = "ID of the veterinarian to deactivate", required = true, example = "1")
            @PathVariable Long id) {
        veterinarianService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}