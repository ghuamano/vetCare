package com.vetcare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Veterinarian information")
public class VeterinarianDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "First name", example = "Ana")
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @Schema(description = "Last name", example = "Martinez")
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Schema(description = "License number", example = "VET-001")
    @NotBlank(message = "License number is required")
    private String licenseNumber;
    
    @Schema(description = "Email", example = "ana.martinez@vetcare.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Schema(description = "Phone number", example = "+57-300-1111111")
    private String phone;
    
    @Schema(description = "Photo URL")
    private String photoUrl;
    
    @Schema(description = "Active status")
    private Boolean active;
    
    @Schema(description = "Clinic information")
    private ClinicSummaryDTO clinic;
    
    @Schema(description = "List of specialties")
    private List<SpecialtyDTO> specialties;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

}
