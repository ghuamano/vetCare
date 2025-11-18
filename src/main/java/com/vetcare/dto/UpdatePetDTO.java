package com.vetcare.dto;

import com.vetcare.models.Pet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating a pet")
public class UpdatePetDTO {

    @Schema(description = "Pet's name", example = "Max", required = true)
    @NotBlank(message = "Pet name is required")
    private String name;
    
    @Schema(description = "Birth date", example = "2020-05-15", required = true)
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
    
    @Schema(description = "Breed", example = "Golden Retriever")
    private String breed;
    
    @Schema(description = "Gender", example = "MALE")
    private Pet.Gender gender;
    
    @Schema(description = "Color", example = "Golden")
    private String color;
    
    @Schema(description = "Weight in kilograms", example = "30.5")
    private Double weight;
    
    @Schema(description = "Medical notes", example = "Allergic to chicken")
    private String medicalNotes;
    
    @Schema(description = "Photo URL")
    private String photoUrl;

}
