package com.vetcare.services;

import com.vetcare.dto.PetTypeDTO;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.exceptions.DuplicateResourceException;
import com.vetcare.models.PetType;
import com.vetcare.repositories.PetTypeRepository;
import com.vetcare.mappers.PetTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PetTypeService {

    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;
    
    @Transactional(readOnly = true)
    public List<PetTypeDTO> findAll() {
        log.debug("Finding all pet types");
        List<PetType> petTypes = petTypeRepository.findAll();
        return petTypeMapper.toDTOList(petTypes);
    }
    
    @Transactional(readOnly = true)
    public List<PetTypeDTO> findAllActive() {
        log.debug("Finding all active pet types");
        List<PetType> petTypes = petTypeRepository.findByActiveTrue();
        return petTypeMapper.toDTOList(petTypes);
    }
    
    @Transactional(readOnly = true)
    public PetTypeDTO findById(Long id) {
        log.debug("Finding pet type by id: {}", id);
        PetType petType = petTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet type not found with id: " + id));
        return petTypeMapper.toDTO(petType);
    }
    
    @Transactional
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {
        log.info("Creating new pet type: {}", petTypeDTO.getName());
        
        if (petTypeRepository.existsByName(petTypeDTO.getName())) {
            throw new DuplicateResourceException("Pet type already exists: " + petTypeDTO.getName());
        }
        
        PetType petType = petTypeMapper.toEntity(petTypeDTO);
        PetType savedPetType = petTypeRepository.save(petType);
        return petTypeMapper.toDTO(savedPetType);
    }
    
    @Transactional
    public PetTypeDTO update(Long id, PetTypeDTO petTypeDTO) {
        log.info("Updating pet type with id: {}", id);
        
        PetType existingPetType = petTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet type not found with id: " + id));
        
        if (!existingPetType.getName().equals(petTypeDTO.getName()) &&
            petTypeRepository.existsByName(petTypeDTO.getName())) {
            throw new DuplicateResourceException("Pet type already exists: " + petTypeDTO.getName());
        }
        
        petTypeMapper.updateEntityFromDTO(petTypeDTO, existingPetType);
        PetType savedPetType = petTypeRepository.save(existingPetType);
        return petTypeMapper.toDTO(savedPetType);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting pet type with id: {}", id);
        PetType petType = petTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet type not found with id: " + id));
        petTypeRepository.delete(petType);
    }

}
