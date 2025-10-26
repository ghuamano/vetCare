package com.vetcare.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Specialty name is required")
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // Relación N:M con Veterinarian (lado inverso)
    @ManyToMany(mappedBy = "specialties")
    @JsonIgnoreProperties({"specialties", "clinic", "visits"})
    @Builder.Default
    private Set<Veterinarian> veterinarians = new HashSet<>();

}
