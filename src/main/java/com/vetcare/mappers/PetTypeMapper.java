package com.vetcare.mappers;

import com.vetcare.dto.PetTypeDTO;
import com.vetcare.models.PetType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public class PetTypeMapper {

    // Entity to DTO
    PetTypeDTO toDTO(PetType petType);
    
    List<PetTypeDTO> toDTOList(List<PetType> petTypes);
    
    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "pets", ignore = true)
    PetType toEntity(PetTypeDTO petTypeDTO);
    
    // Update Entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "pets", ignore = true)
    void updateEntityFromDTO(PetTypeDTO petTypeDTO, @MappingTarget PetType petType);

}
