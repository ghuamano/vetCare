package com.vetcare.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Clinic name is required")
    @Column(nullable = false, length = 150)
    private String name;
    
    @NotBlank(message = "Address is required")
    @Column(nullable = false, length = 255)
    private String address;
    
    @NotBlank(message = "City is required")
    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 20)
    private String phone;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 500)
    private String workingHours;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relación 1:N con Veterinarian
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"clinic", "specialties", "visits"})
    @Builder.Default
    private List<Veterinarian> veterinarians = new ArrayList<>();
    
    // Relación 1:N con Visit
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"clinic", "pet", "veterinarian"})
    @Builder.Default
    private List<Visit> visits = new ArrayList<>();

}
