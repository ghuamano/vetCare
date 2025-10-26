package com.vetcare.models;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pet_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pet type name is required")
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // Relación 1:N con Pet
    @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"petType", "owner", "visits"})
    @Builder.Default
    private List<Pet> pets = new ArrayList<>();
}
