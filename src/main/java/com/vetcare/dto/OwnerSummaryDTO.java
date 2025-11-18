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
@Schema(description = "Owner summary information")
public class OwnerSummaryDTO {

    @Schema(description = "Unique identifier")
    private Long id;
    
    @Schema(description = "Full name")
    private String fullName;
    
    @Schema(description = "Email address")
    private String email;
    
    @Schema(description = "Phone number")
    private String phone;
}
