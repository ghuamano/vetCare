
package com.vetcare.mappers;

import com.vetcare.dto.*;
import com.vetcare.models.Owner;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface OwnerMapper {

     // Entity to DTO
    OwnerDTO toDTO(Owner owner);
    
    List<OwnerDTO> toDTOList(List<Owner> owners);
    
    // Entity to OwnerWithPetsDTO
    @Mapping(target = "pets", source = "pets")
    OwnerWithPetsDTO toOwnerWithPetsDTO(Owner owner);
    
    // Entity to OwnerSummaryDTO
    @Mapping(target = "fullName", expression = "java(owner.getFirstName() + \" \" + owner.getLastName())")
    OwnerSummaryDTO toSummaryDTO(Owner owner);
    
    // CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "pets", ignore = true)
    Owner toEntity(CreateOwnerDTO createOwnerDTO);
    
    // Update Entity from UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pets", ignore = true)
    void updateEntityFromDTO(UpdateOwnerDTO updateOwnerDTO, @MappingTarget Owner owner);

}
