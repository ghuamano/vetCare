package com.vetcare.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Owner information including their pets")
public class OwnerWithPetsDTO {

    @Schema(description = "Unique identifier")
    private Long id;
    
    @Schema(description = "Owner's first name")
    private String firstName;
    
    @Schema(description = "Owner's last name")
    private String lastName;
    
    @Schema(description = "Email address")
    private String email;
    
    @Schema(description = "Phone number")
    private String phone;
    
    @Schema(description = "City")
    private String city;
    
    @Schema(description = "List of pets owned")
    private List<PetSummaryDTO> pets;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

}
