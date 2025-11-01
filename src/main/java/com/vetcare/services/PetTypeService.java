package com.vetcare.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.exceptions.DuplicateResourceException;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.PetType;
import com.vetcare.repositories.PetTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetTypeService {

    private final PetTypeRepository petTypeRepository;

    @Transactional(readOnly = true)
    public List<PetType> findAll() {
        log.debug("Finding all pet types");
        return petTypeRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<PetType> findAllActive() {
        log.debug("Finding all active pet types");
        return petTypeRepository.findByActiveTrue();
    }
    
    @Transactional(readOnly = true)
    public PetType findById(Long id) {
        log.debug("Finding pet type by id: {}", id);
        return petTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet type not found with id: " + id));
    }
    
    @Transactional
    public PetType create(PetType petType) {
        log.info("Creating new pet type: {}", petType.getName());
        
        if (petTypeRepository.existsByName(petType.getName())) {
            throw new DuplicateResourceException("Pet type already exists: " + petType.getName());
        }
        
        return petTypeRepository.save(petType);
    }
    
    @Transactional
    public PetType update(Long id, PetType updatedPetType) {
        log.info("Updating pet type with id: {}", id);
        
        PetType existingPetType = findById(id);
        
        if (!existingPetType.getName().equals(updatedPetType.getName()) &&
            petTypeRepository.existsByName(updatedPetType.getName())) {
            throw new DuplicateResourceException("Pet type already exists: " + updatedPetType.getName());
        }
        
        existingPetType.setName(updatedPetType.getName());
        existingPetType.setDescription(updatedPetType.getDescription());
        
        return petTypeRepository.save(existingPetType);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting pet type with id: {}", id);
        PetType petType = findById(id);
        petTypeRepository.delete(petType);
    }


}
