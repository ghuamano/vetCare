package com.vetcare.mappers;

import com.vetcare.dto.*;
import com.vetcare.models.Veterinarian;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClinicMapper.class, SpecialtyMapper.class})
public class VeterinarianMapper {

    // Entity to DTO
    @Mapping(target = "clinic", source = "clinic")
    @Mapping(target = "specialties", source = "specialties")
    VeterinarianDTO toDTO(Veterinarian veterinarian);
    
    List<VeterinarianDTO> toDTOList(List<Veterinarian> veterinarians);
    
    // Entity to SummaryDTO
    @Mapping(target = "fullName", expression = "java(veterinarian.getFirstName() + \" \" + veterinarian.getLastName())")
    VeterinarianSummaryDTO toSummaryDTO(Veterinarian veterinarian);
    
    // CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "clinic", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    @Mapping(target = "visits", ignore = true)
    Veterinarian toEntity(CreateVeterinarianDTO createVeterinarianDTO);
    
    // Update Entity from UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "licenseNumber", ignore = true)
    @Mapping(target = "clinic", ignore = true)
    @Mapping(target = "specialties", ignore = true)
    @Mapping(target = "visits", ignore = true)
    void updateEntityFromDTO(UpdateVeterinarianDTO updateVeterinarianDTO, @MappingTarget Veterinarian veterinarian);


}
