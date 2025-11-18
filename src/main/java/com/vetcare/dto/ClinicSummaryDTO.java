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
@Schema(description = "Clinic summary information")
public class ClinicSummaryDTO {
    
    @Schema(description = "Unique identifier")
    private Long id;
    
    @Schema(description = "Clinic name")
    private String name;
    
    @Schema(description = "City")
    private String city;
    
    @Schema(description = "Phone number")
    private String phone;
}
