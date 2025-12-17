package com.vetcare.controllers;

import com.vetcare.dto.SpecialtyDTO;
import com.vetcare.services.SpecialtyService;
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
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Specialties", description = "Operations related to veterinary medical specialty catalog management")
public class SpecialtyController {
    
    private final SpecialtyService specialtyService;
    
    @Operation(
        summary = "Get all active specialties",
        description = "Retrieves a list of all active veterinary medical specialties"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of specialties",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpecialtyDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAllActive());
    }
    
    @Operation(
        summary = "Get specialty by ID",
        description = "Retrieves detailed information about a specific medical specialty"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Specialty found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpecialtyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getSpecialtyById(
            @Parameter(description = "ID of the specialty to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }
    
    @Operation(
        summary = "Create new specialty",
        description = "Adds a new medical specialty to the catalog"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Specialty created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpecialtyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Specialty with same name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SpecialtyDTO> createSpecialty(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Specialty object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = SpecialtyDTO.class))
            )
            @Valid @RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO createdSpecialty = specialtyService.create(specialtyDTO);
        return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Update specialty",
        description = "Updates the information of an existing medical specialty"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Specialty updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SpecialtyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Specialty name already in use", content = @Content)
    })
    
    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(
            @Parameter(description = "ID of the specialty") @PathVariable Long id,
            @Valid @RequestBody SpecialtyDTO specialtyDTO) {
        return ResponseEntity.ok(specialtyService.update(id, specialtyDTO));
    }
    
    @Operation(
        summary = "Delete specialty",
        description = "Deletes a medical specialty from the catalog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Specialty deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Specialty not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(
            @Parameter(description = "ID of the specialty to delete", required = true, example = "1") 
            @PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
