package com.vetcare.controllers;

import com.vetcare.dto.ClinicDTO;
import com.vetcare.dto.CreateClinicDTO;
import com.vetcare.services.ClinicService;
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
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clinics", description = "Operations related to veterinary clinic location management")
public class ClinicController {
    
    private final ClinicService clinicService;
    
    @Operation(
        summary = "Get all active clinics",
        description = "Retrieves a list of all active veterinary clinic locations"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clinics",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClinicDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAllActive());
    }
    
    @Operation(
        summary = "Get clinic by ID",
        description = "Retrieves detailed information about a specific clinic location"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clinic found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClinicDTO.class))),
        @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(
            @Parameter(description = "ID of the clinic to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(clinicService.findById(id));
    }
    
    @Operation(
        summary = "Get clinics by city",
        description = "Retrieves all clinic locations in a specific city"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved clinics in the city")
    })
    @GetMapping("/city/{city}")
    public ResponseEntity<List<ClinicDTO>> getClinicsByCity(
            @Parameter(description = "Name of the city", required = true, example = "Bogotá")
            @PathVariable String city) {
        return ResponseEntity.ok(clinicService.findByCity(city));
    }
    
    @Operation(
        summary = "Create new clinic",
        description = "Registers a new veterinary clinic location in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Clinic created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClinicDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClinicDTO> createClinic(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Clinic object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = CreateClinicDTO.class))
            )
            @Valid @RequestBody CreateClinicDTO createClinicDTO) {
        ClinicDTO createdClinic = clinicService.create(createClinicDTO);
        return new ResponseEntity<>(createdClinic, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Update clinic",
        description = "Updates the information of an existing clinic location"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clinic updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClinicDTO.class))),
        @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClinicDTO> updateClinic(
            @Parameter(description = "ID of the clinic to update", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClinicDTO clinicDTO) {
        return ResponseEntity.ok(clinicService.update(id, clinicDTO));
    }
    
    @Operation(
        summary = "Delete clinic",
        description = "Permanently deletes a clinic location from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Clinic deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(
            @Parameter(description = "ID of the clinic to delete", required = true, example = "1")
            @PathVariable Long id) {
        clinicService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
        summary = "Deactivate clinic",
        description = "Soft deletes a clinic by marking it as inactive"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Clinic deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content)
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateClinic(
            @Parameter(description = "ID of the clinic to deactivate", required = true, example = "1")
            @PathVariable Long id) {
        clinicService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}