package com.vetcare.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Visit date is required")
    @Column(nullable = false)
    private LocalDateTime visitDate;
    
    @NotBlank(message = "Reason is required")
    @Column(nullable = false, length = 500)
    private String reason;
    
    @Column(columnDefinition = "TEXT")
    private String diagnosis;
    
    @Column(columnDefinition = "TEXT")
    private String treatment;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal cost;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private VisitStatus status = VisitStatus.SCHEDULED;
    
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Relación N:1 con Pet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnoreProperties({"visits", "owner", "hibernateLazyInitializer"})
    private Pet pet;
    
    // Relación N:1 con Veterinarian
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    @JsonIgnoreProperties({"visits", "specialties", "clinic", "hibernateLazyInitializer"})
    private Veterinarian veterinarian;
    
    // Relación N:1 con Clinic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    @JsonIgnoreProperties({"visits", "veterinarians", "hibernateLazyInitializer"})
    private Clinic clinic;
    
    public enum VisitStatus {
        SCHEDULED,    // Programada
        IN_PROGRESS,  // En proceso
        COMPLETED,    // Completada
        CANCELLED     // Cancelada
    }
}
