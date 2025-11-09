package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetcare.models.Owner;
import com.vetcare.services.OwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Owners", description = "API for managing pet owners")
public class OwnerController {

        private final OwnerService ownerService;

        @Operation(summary = "List all active owners", 
                description = "Retrieves a list of all registered and active owners in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List retrieved successfully")
        })
        @GetMapping
        public ResponseEntity<List<Owner>> getAllOwners() {
                return ResponseEntity.ok(ownerService.findAllActive());
        }

        @Operation(summary = "Get owner by ID", description = "Retrieves details of a specific owner")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Owner found"),
                        @ApiResponse(responseCode = "404", description = "Owner not found")
        })
        @GetMapping("/{id}")
        public ResponseEntity<Owner> getOwnerById(
                        @Parameter(description = "Owner ID") @PathVariable Long id) {
                return ResponseEntity.ok(ownerService.findById(id));
        }

        @Operation(summary = "Get owner with their pets", 
                description = "Retrieves an owner including all their registered pets")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Owner with pets found"),
                        @ApiResponse(responseCode = "404", description = "Owner not found")
        })
        @GetMapping("/{id}/pets")
        public ResponseEntity<Owner> getOwnerWithPets(
                        @Parameter(description = "Owner ID") @PathVariable Long id) {
                return ResponseEntity.ok(ownerService.findByIdWithPets(id));
        }

        @Operation(summary = "Search owners by name", description = "Searches owners by first or last name (partial match)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Search completed")
        })
        @GetMapping("/search")
        public ResponseEntity<List<Owner>> searchOwners(
                        @Parameter(description = "First or last name to search") @RequestParam String name) {
                return ResponseEntity.ok(ownerService.searchByName(name));
        }

        @Operation(summary = "Create new owner", description = "Registers a new owner in the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Owner created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid data"),
                        @ApiResponse(responseCode = "409", description = "Duplicate email or document")
        })
        @PostMapping
        public ResponseEntity<Owner> createOwner(@Valid @RequestBody Owner owner) {
                Owner createdOwner = ownerService.create(owner);
                return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
        }

        @Operation(summary = "Update owner", description = "Updates information of an existing owner")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Owner updated successfully"),
                        @ApiResponse(responseCode = "404", description = "Owner not found"),
                        @ApiResponse(responseCode = "409", description = "Duplicate email")
        })
        @PutMapping("/{id}")
        public ResponseEntity<Owner> updateOwner(
                        @Parameter(description = "Owner ID") @PathVariable Long id,
                        @Valid @RequestBody Owner owner) {
                return ResponseEntity.ok(ownerService.update(id, owner));
        }

        @Operation(summary = "Delete owner", description = "Permanently deletes an owner from the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Owner not found")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteOwner(
                        @Parameter(description = "Owner ID") @PathVariable Long id) {
                ownerService.delete(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Deactivate owner", description = "Deactivates an owner without deleting them from the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Owner deactivated successfully"),
                        @ApiResponse(responseCode = "404", description = "Owner not found")
        })
        @PatchMapping("/{id}/deactivate")
        public ResponseEntity<Void> deactivateOwner(
                        @Parameter(description = "Owner ID") @PathVariable Long id) {
                ownerService.deactivate(id);
                return ResponseEntity.noContent().build();
        }

}
