package com.vetcare.dto;

import com.vetcare.models.Visit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating a visit")
public class UpdateVisitDTO {

    @Schema(description = "Visit date and time", example = "2025-10-30T10:00:00")
    @NotNull(message = "Visit date is required")
    private LocalDateTime visitDate;
    
    @Schema(description = "Reason for visit", example = "Annual checkup")
    @NotBlank(message = "Reason is required")
    private String reason;
    
    @Schema(description = "Diagnosis", example = "Healthy condition")
    private String diagnosis;
    
    @Schema(description = "Treatment prescribed", example = "Vaccination applied")
    private String treatment;
    
    @Schema(description = "Additional notes", example = "Schedule follow-up in 6 months")
    private String notes;
    
    @Schema(description = "Cost", example = "50000.00")
    private BigDecimal cost;
    
    @Schema(description = "Visit status", example = "COMPLETED")
    private Visit.VisitStatus status;

}
