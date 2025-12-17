package com.vetcare.controllers;

import com.vetcare.dto.PetTypeDTO;
import com.vetcare.services.PetTypeService;
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
@RequestMapping("/api/pet-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pet Types", description = "Operations related to pet type catalog management")
public class PetTypeController {
    
    private final PetTypeService petTypeService;
    
    @Operation(
        summary = "Get all active pet types",
        description = "Retrieves a list of all active pet types (e.g., Dog, Cat, Bird)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pet types",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PetTypeDTO>> getAllPetTypes() {
        return ResponseEntity.ok(petTypeService.findAllActive());
    }
    
    @Operation(
        summary = "Get pet type by ID",
        description = "Retrieves detailed information about a specific pet type"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pet type found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pet type not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetTypeDTO> getPetTypeById(
            @Parameter(description = "ID of the pet type to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(petTypeService.findById(id));
    }
    
    @Operation(
        summary = "Create new pet type",
        description = "Adds a new pet type to the catalog"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pet type created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Pet type with same name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PetTypeDTO> createPetType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Pet type object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = PetTypeDTO.class))
            )
            @Valid @RequestBody PetTypeDTO petTypeDTO) {
        PetTypeDTO createdPetType = petTypeService.create(petTypeDTO);
        return new ResponseEntity<>(createdPetType, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Update pet type",
        description = "Updates the information of an existing pet type"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pet type updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetTypeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pet type not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Pet type name already in use", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PetTypeDTO> updatePetType(
            @Parameter(description = "ID of the pet type to update", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PetTypeDTO petTypeDTO) {
        return ResponseEntity.ok(petTypeService.update(id, petTypeDTO));
    }
    
    @Operation(
        summary = "Delete pet type",
        description = "Permanently deletes a pet type from the catalog"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pet type deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Pet type not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(
            @Parameter(description = "ID of the pet type to delete", required = true, example = "1")
            @PathVariable Long id) {
        petTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}