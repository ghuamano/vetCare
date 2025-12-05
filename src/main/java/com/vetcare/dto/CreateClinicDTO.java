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
@Schema(description = "Request body for creating a new clinic")
public class CreateClinicDTO {

    @Schema(description = "Clinic name", example = "VetCare Centro", required = true)
    @NotBlank(message = "Clinic name is required")
    private String name;
    
    @Schema(description = "Address", example = "123 Main St", required = true)
    @NotBlank(message = "Address is required")
    private String address;
    
    @Schema(description = "City", example = "Bogotá", required = true)
    @NotBlank(message = "City is required")
    private String city;
    
    @Schema(description = "Phone number", example = "+57-1-234-5678")
    private String phone;
    
    @Schema(description = "Email", example = "centro@vetcare.com")
    private String email;
    
    @Schema(description = "Working hours", example = "Mon-Fri: 8am-6pm")
    private String workingHours;
    
}
