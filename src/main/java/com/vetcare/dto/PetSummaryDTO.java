package com.vetcare.dto;

import com.vetcare.models.Pet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pet summary information")
public class PetSummaryDTO {

    @Schema(description = "Unique identifier")
    private Long id;
    
    @Schema(description = "Pet's name")
    private String name;
    
    @Schema(description = "Birth date")
    private LocalDate birthDate;
    
    @Schema(description = "Breed")
    private String breed;
    
    @Schema(description = "Gender")
    private Pet.Gender gender;
    
    @Schema(description = "Pet type name")
    private String petTypeName;
}
