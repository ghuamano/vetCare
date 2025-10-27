package com.vetcare.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.models.Specialty;
import com.vetcare.repositories.SpecialtyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Transactional(readOnly = true)
    public List<Specialty> findAll() {
        log.debug("Finding all specialties");
        return specialtyRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Specialty> findAllActive() {
        log.debug("Finding all active specialties");
        return specialtyRepository.findByActiveTrue();
    }
    
    @Transactional(readOnly = true)
    public Specialty findById(Long id) {
        log.debug("Finding specialty by id: {}", id);
        return specialtyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));
    }
    
    @Transactional
    public Specialty create(Specialty specialty) {
        log.info("Creating new specialty: {}", specialty.getName());
        
        if (specialtyRepository.existsByName(specialty.getName())) {
            throw new DuplicateResourceException("Specialty already exists: " + specialty.getName());
        }
        
        return specialtyRepository.save(specialty);
    }
    
    @Transactional
    public Specialty update(Long id, Specialty updatedSpecialty) {
        log.info("Updating specialty with id: {}", id);
        
        Specialty existingSpecialty = findById(id);
        
        if (!existingSpecialty.getName().equals(updatedSpecialty.getName()) &&
            specialtyRepository.existsByName(updatedSpecialty.getName())) {
            throw new DuplicateResourceException("Specialty already exists: " + updatedSpecialty.getName());
        }
        
        existingSpecialty.setName(updatedSpecialty.getName());
        existingSpecialty.setDescription(updatedSpecialty.getDescription());
        
        return specialtyRepository.save(existingSpecialty);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting specialty with id: {}", id);
        Specialty specialty = findById(id);
        specialtyRepository.delete(specialty);
    }

}
