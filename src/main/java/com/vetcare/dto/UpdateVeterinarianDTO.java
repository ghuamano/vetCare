package com.vetcare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating a veterinarian")

public class UpdateVeterinarianDTO {

    @Schema(description = "First name", example = "Ana")
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @Schema(description = "Last name", example = "Martinez")
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Schema(description = "Email", example = "ana.martinez@vetcare.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Schema(description = "Phone number", example = "+57-300-1111111")
    private String phone;
    
    @Schema(description = "Photo URL")
    private String photoUrl;
}
