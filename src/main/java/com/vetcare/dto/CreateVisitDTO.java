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
@Schema(description = "Request body for creating a new visit")
public class CreateVisitDTO {

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
    
    @Schema(description = "Visit status", example = "SCHEDULED")
    private Visit.VisitStatus status;
    
    @Schema(description = "Pet ID", example = "1")
    @NotNull(message = "Pet ID is required")
    private Long petId;
    
    @Schema(description = "Veterinarian ID", example = "1")
    @NotNull(message = "Veterinarian ID is required")
    private Long veterinarianId;
    
    @Schema(description = "Clinic ID", example = "1")
    @NotNull(message = "Clinic ID is required")
    private Long clinicId;
}
