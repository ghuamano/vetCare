package com.vetcare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Veterinarian summary information")
public class VeterinarianSummaryDTO {

    @Schema(description = "Unique identifier")
    private Long id;
    
    @Schema(description = "Full name")
    private String fullName;
    
    @Schema(description = "License number")
    private String licenseNumber;
    
    @Schema(description = "Email")
    private String email;
}
