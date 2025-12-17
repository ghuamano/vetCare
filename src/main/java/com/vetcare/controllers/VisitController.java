package com.vetcare.controllers;

import com.vetcare.dto.*;
import com.vetcare.models.Visit;
import com.vetcare.services.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Visits", description = "Operations related to medical visit and appointment management")
public class VisitController {
    
    private final VisitService visitService;
    
    @Operation(
        summary = "Get all visits",
        description = "Retrieves a list of all medical visits in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of visits",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<VisitDTO>> getAllVisits() {
        return ResponseEntity.ok(visitService.findAll());
    }
    
    @Operation(
        summary = "Get visit by ID",
        description = "Retrieves detailed information about a specific medical visit including pet, veterinarian, and clinic"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visit found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDTO.class))),
        @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisitById(
            @Parameter(description = "ID of the visit to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(visitService.findById(id));
    }
    
    @Operation(
        summary = "Get visits by pet",
        description = "Retrieves all medical visits for a specific pet (medical history)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved pet's visit history")
    })
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<VisitDTO>> getVisitsByPet(
            @Parameter(description = "ID of the pet", required = true, example = "1")
            @PathVariable Long petId) {
        return ResponseEntity.ok(visitService.findByPetId(petId));
    }
    
    @Operation(
        summary = "Get visits by veterinarian",
        description = "Retrieves all visits attended by a specific veterinarian"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved veterinarian's visits")
    })
    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<VisitDTO>> getVisitsByVeterinarian(
            @Parameter(description = "ID of the veterinarian", required = true, example = "1")
            @PathVariable Long veterinarianId) {
        return ResponseEntity.ok(visitService.findByVeterinarianId(veterinarianId));
    }
    
    @Operation(
        summary = "Get visits by date range",
        description = "Retrieves all visits within a specific date and time range"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved visits in date range")
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<VisitDTO>> getVisitsByDateRange(
            @Parameter(description = "Start date and time (ISO-8601 format)", required = true, example = "2025-10-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date and time (ISO-8601 format)", required = true, example = "2025-10-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(visitService.findByDateRange(startDate, endDate));
    }
    
    @Operation(
        summary = "Create new visit",
        description = "Schedules a new medical visit or appointment"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Visit created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pet, veterinarian, or clinic not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VisitDTO> createVisit(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Visit object to be created (includes petId, veterinarianId, clinicId)",
                required = true,
                content = @Content(schema = @Schema(implementation = CreateVisitDTO.class))
            )
            @Valid @RequestBody CreateVisitDTO createVisitDTO) {
        VisitDTO createdVisit = visitService.create(createVisitDTO);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Update visit",
        description = "Updates the information of an existing medical visit"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visit updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDTO.class))),
        @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitDTO> updateVisit(
            @Parameter(description = "ID of the visit to update", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated visit information",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdateVisitDTO.class))
            )
            @Valid @RequestBody UpdateVisitDTO updateVisitDTO) {
        return ResponseEntity.ok(visitService.update(id, updateVisitDTO));
    }
    
    @Operation(
        summary = "Update visit status",
        description = "Updates the status of a visit (e.g., SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visit status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDTO.class))),
        @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<VisitDTO> updateVisitStatus(
            @Parameter(description = "ID of the visit", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "New status", required = true, example = "COMPLETED")
            @RequestParam Visit.VisitStatus status) {
        return ResponseEntity.ok(visitService.updateStatus(id, status));
    }
    
    @Operation(
        summary = "Delete visit",
        description = "Permanently deletes a visit from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Visit deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(
            @Parameter(description = "ID of the visit to delete", required = true, example = "1")
            @PathVariable Long id) {
        visitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}