package com.vetcare.services;

import com.vetcare.dto.SpecialtyDTO;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.exceptions.DuplicateResourceException;
import com.vetcare.models.Specialty;
import com.vetcare.repositories.SpecialtyRepository;
import com.vetcare.mappers.SpecialtyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;
    
    @Transactional(readOnly = true)
    public List<SpecialtyDTO> findAll() {
        log.debug("Finding all specialties");
        List<Specialty> specialties = specialtyRepository.findAll();
        return specialtyMapper.toDTOList(specialties);
    }
    
    @Transactional(readOnly = true)
    public List<SpecialtyDTO> findAllActive() {
        log.debug("Finding all active specialties");
        List<Specialty> specialties = specialtyRepository.findByActiveTrue();
        return specialtyMapper.toDTOList(specialties);
    }
    
    @Transactional(readOnly = true)
    public SpecialtyDTO findById(Long id) {
        log.debug("Finding specialty by id: {}", id);
        Specialty specialty = specialtyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));
        return specialtyMapper.toDTO(specialty);
    }
    
    @Transactional(readOnly = true)
    public Specialty findEntityById(Long id) {
        return specialtyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));
    }
    
    @Transactional
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        log.info("Creating new specialty: {}", specialtyDTO.getName());
        
        if (specialtyRepository.existsByName(specialtyDTO.getName())) {
            throw new DuplicateResourceException("Specialty already exists: " + specialtyDTO.getName());
        }
        
        Specialty specialty = specialtyMapper.toEntity(specialtyDTO);
        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toDTO(savedSpecialty);
    }
    
    @Transactional
    public SpecialtyDTO update(Long id, SpecialtyDTO specialtyDTO) {
        log.info("Updating specialty with id: {}", id);
        
        Specialty existingSpecialty = specialtyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));
        
        if (!existingSpecialty.getName().equals(specialtyDTO.getName()) &&
            specialtyRepository.existsByName(specialtyDTO.getName())) {
            throw new DuplicateResourceException("Specialty already exists: " + specialtyDTO.getName());
        }
        
        specialtyMapper.updateEntityFromDTO(specialtyDTO, existingSpecialty);
        Specialty savedSpecialty = specialtyRepository.save(existingSpecialty);
        return specialtyMapper.toDTO(savedSpecialty);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting specialty with id: {}", id);
        Specialty specialty = specialtyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));
        specialtyRepository.delete(specialty);
    }
}
