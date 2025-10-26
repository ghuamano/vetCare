package com.vetcare.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Pet name is required")
    @Column(nullable = false, length = 100)
    private String name;
    
    @NotNull(message = "Birth date is required")
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(length = 50)
    private String breed;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;
    
    @Column(length = 50)
    private String color;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;
    
    @Column(length = 500)
    private String medicalNotes;
    
    @Column(length = 255)
    private String photoUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Relación N:1 con Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"pets", "hibernateLazyInitializer"})
    private Owner owner;
    
    // Relación N:1 con PetType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_type_id", nullable = false)
    @JsonIgnoreProperties({"pets", "hibernateLazyInitializer"})
    private PetType petType;
    
    // Relación N:1 con Clinic (opcional - clínica principal)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    @JsonIgnoreProperties({"veterinarians", "visits", "hibernateLazyInitializer"})
    private Clinic primaryClinic;
    
    // Relación 1:N con Visit
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"pet", "veterinarian", "clinic"})
    @Builder.Default
    private List<Visit> visits = new ArrayList<>();
    
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

}
