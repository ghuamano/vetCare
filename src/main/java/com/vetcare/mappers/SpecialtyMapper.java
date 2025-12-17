package com.vetcare.mappers;

import com.vetcare.dto.SpecialtyDTO;
import com.vetcare.models.Specialty;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public class SpecialtyMapper {

    // Entity to DTO
    SpecialtyDTO toDTO(Specialty specialty);
    
    List<SpecialtyDTO> toDTOList(List<Specialty> specialties);
    
    Set<SpecialtyDTO> toDTOSet(Set<Specialty> specialties);
    
    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "veterinarians", ignore = true)
    Specialty toEntity(SpecialtyDTO specialtyDTO);
    
    // Update Entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "veterinarians", ignore = true)
    void updateEntityFromDTO(SpecialtyDTO specialtyDTO, @MappingTarget Specialty specialty);

}
