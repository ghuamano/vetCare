package com.vetcare.dto;

import java.time.LocalDateTime;

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
@Schema(description = "Pet owner basic information")
public class OwnerDTO {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Owner's first name", example = "John")
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @Schema(description = "Owner's last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Schema(description = "Document number (ID)", example = "123456789")
    private String documentNumber;
    
    @Schema(description = "Email address", example = "john.doe@email.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Schema(description = "Phone number", example = "+1-555-0123")
    private String phone;
    
    @Schema(description = "Street address", example = "123 Main St")
    private String address;
    
    @Schema(description = "City", example = "New York")
    private String city;
    
    @Schema(description = "Active status", example = "true")
    private Boolean active;
    
    @Schema(description = "Creation timestamp", example = "2025-10-26T10:30:00")
    private LocalDateTime createdAt;

}
