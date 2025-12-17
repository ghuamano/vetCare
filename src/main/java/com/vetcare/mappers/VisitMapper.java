package com.vetcare.mappers;

import com.vetcare.dto.*;
import com.vetcare.models.Visit;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, VeterinarianMapper.class, ClinicMapper.class})

public class VisitMapper {

    // Entity to DTO
    @Mapping(target = "pet", source = "pet")
    @Mapping(target = "veterinarian", source = "veterinarian")
    @Mapping(target = "clinic", source = "clinic")
    VisitDTO toDTO(Visit visit);
    
    List<VisitDTO> toDTOList(List<Visit> visits);
    
    // CreateDTO to Entity (without relationships)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "pet", ignore = true)
    @Mapping(target = "veterinarian", ignore = true)
    @Mapping(target = "clinic", ignore = true)
    Visit toEntity(CreateVisitDTO createVisitDTO);
    
    // Update Entity from UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pet", ignore = true)
    @Mapping(target = "veterinarian", ignore = true)
    @Mapping(target = "clinic", ignore = true)
    void updateEntityFromDTO(UpdateVisitDTO updateVisitDTO, @MappingTarget Visit visit);

}
