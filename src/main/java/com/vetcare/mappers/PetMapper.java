package com.vetcare.mappers;

import com.vetcare.dto.*;
import com.vetcare.models.Pet;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class, PetTypeMapper.class, ClinicMapper.class})
public class PetMapper {

    // Entity to DTO
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "petType", source = "petType")
    @Mapping(target = "primaryClinic", source = "primaryClinic")
    PetDTO toDTO(Pet pet);
    
    List<PetDTO> toDTOList(List<Pet> pets);
    
    // Entity to PetSummaryDTO
    @Mapping(target = "petTypeName", source = "petType.name")
    PetSummaryDTO toSummaryDTO(Pet pet);
    
    List<PetSummaryDTO> toSummaryDTOList(List<Pet> pets);
    
    // CreateDTO to Entity (without relationships)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "petType", ignore = true)
    @Mapping(target = "primaryClinic", ignore = true)
    @Mapping(target = "visits", ignore = true)
    Pet toEntity(CreatePetDTO createPetDTO);
    
    // Update Entity from UpdateDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "petType", ignore = true)
    @Mapping(target = "primaryClinic", ignore = true)
    @Mapping(target = "visits", ignore = true)
    void updateEntityFromDTO(UpdatePetDTO updatePetDTO, @MappingTarget Pet pet);

}
