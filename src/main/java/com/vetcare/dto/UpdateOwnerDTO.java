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
@Schema(description = "Request body for updating an owner")
public class UpdateOwnerDTO {

    @Schema(description = "Owner's first name", example = "John", required = true)
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @Schema(description = "Owner's last name", example = "Doe", required = true)
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Schema(description = "Document number (ID)", example = "123456789")
    private String documentNumber;
    
    @Schema(description = "Email address", example = "john.doe@email.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Schema(description = "Phone number", example = "+1-555-0123")
    private String phone;
    
    @Schema(description = "Street address", example = "123 Main St")
    private String address;
    
    @Schema(description = "City", example = "New York")
    private String city;
}
