package com.vetcare.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Clinic information")
public class ClinicDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "Clinic name", example = "VetCare Centro", required = true)
    @NotBlank(message = "Clinic name is required")
    private String name;
    
    @Schema(description = "Address", example = "123 Main St", required = true)
    @NotBlank(message = "Address is required")
    private String address;
    
    @Schema(description = "City", example = "Bogotá")
    @NotBlank(message = "City is required")
    private String city;
    
    @Schema(description = "Phone number", example = "+57-1-234-5678")
    private String phone;
    
    @Schema(description = "Email", example = "centro@vetcare.com")
    private String email;
    
    @Schema(description = "Working hours", example = "Mon-Fri: 8am-6pm")
    private String workingHours;
    
    @Schema(description = "Active status")
    private Boolean active;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;


}
