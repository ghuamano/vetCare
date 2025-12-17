package com.vetcare.mappers;

import com.vetcare.dto.ClinicDTO;
import com.vetcare.dto.ClinicSummaryDTO;
import com.vetcare.dto.CreateClinicDTO;
import com.vetcare.models.Clinic;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public class ClinicMapper {

    // Entity to DTO
    ClinicDTO toDTO(Clinic clinic);
    
    List<ClinicDTO> toDTOList(List<Clinic> clinics);
    
    // Entity to SummaryDTO
    ClinicSummaryDTO toSummaryDTO(Clinic clinic);
    
    // CreateDTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "veterinarians", ignore = true)
    @Mapping(target = "visits", ignore = true)
    Clinic toEntity(CreateClinicDTO createClinicDTO);
    
    // Update Entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "veterinarians", ignore = true)
    @Mapping(target = "visits", ignore = true)
    void updateEntityFromDTO(ClinicDTO clinicDTO, @MappingTarget Clinic clinic);

}
