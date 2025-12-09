package com.vetcare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Medical specialty information")
public class SpecialtyDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "Specialty name", example = "Surgery")
    @NotBlank(message = "Specialty name is required")
    private String name;
    
    @Schema(description = "Description", example = "Surgical procedures")
    private String description;
    
    @Schema(description = "Active status")
    private Boolean active;
}
