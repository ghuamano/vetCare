package com.vetcare.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "veterinarians")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(nullable = false, length = 100)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(nullable = false, length = 100)
    private String lastName;
    
    @NotBlank(message = "License number is required")
    @Column(nullable = false, unique = true, length = 50)
    private String licenseNumber;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 255)
    private String photoUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Relación N:1 con Clinic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    @JsonIgnoreProperties({"veterinarians", "visits", "hibernateLazyInitializer"})
    private Clinic clinic;
    
    // Relación N:M con Specialty (lado propietario)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "veterinarian_specialties",
        joinColumns = @JoinColumn(name = "veterinarian_id"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    
    @JsonIgnoreProperties({"veterinarians"})
    @Builder.Default
    private Set<Specialty> specialties = new HashSet<>();
    
    // Relación 1:N con Visit
    @OneToMany(mappedBy = "veterinarian", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"veterinarian", "pet", "clinic"})
    @Builder.Default
    private List<Visit> visits = new ArrayList<>();
}
