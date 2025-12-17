package com.vetcare.services;

import com.vetcare.dto.*;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.exceptions.DuplicateResourceException;
import com.vetcare.models.Veterinarian;
import com.vetcare.models.Specialty;
import com.vetcare.models.Clinic;
import com.vetcare.repositories.VeterinarianRepository;
import com.vetcare.repositories.ClinicRepository;
import com.vetcare.mappers.VeterinarianMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;
    private final ClinicRepository clinicRepository;
    private final SpecialtyService specialtyService;
    private final VeterinarianMapper veterinarianMapper;
    
    @Transactional(readOnly = true)
    public List<VeterinarianDTO> findAll() {
        log.debug("Finding all veterinarians");
        List<Veterinarian> veterinarians = veterinarianRepository.findAll();
        return veterinarianMapper.toDTOList(veterinarians);
    }
    
    @Transactional(readOnly = true)
    public List<VeterinarianDTO> findAllActive() {
        log.debug("Finding all active veterinarians");
        List<Veterinarian> veterinarians = veterinarianRepository.findByActiveTrue();
        return veterinarianMapper.toDTOList(veterinarians);
    }
    
    @Transactional(readOnly = true)
    public VeterinarianDTO findById(Long id) {
        log.debug("Finding veterinarian by id: {}", id);
        Veterinarian veterinarian = veterinarianRepository.findByIdWithSpecialties(id)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
        return veterinarianMapper.toDTO(veterinarian);
    }
    
    @Transactional(readOnly = true)
    public List<VeterinarianDTO> findBySpecialty(Long specialtyId) {
        log.debug("Finding veterinarians by specialty id: {}", specialtyId);
        List<Veterinarian> veterinarians = veterinarianRepository.findBySpecialtyId(specialtyId);
        return veterinarianMapper.toDTOList(veterinarians);
    }
    
    @Transactional
    public VeterinarianDTO create(CreateVeterinarianDTO createVeterinarianDTO) {
        log.info("Creating new veterinarian: {} {}", createVeterinarianDTO.getFirstName(), createVeterinarianDTO.getLastName());
        
        if (veterinarianRepository.findByEmail(createVeterinarianDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + createVeterinarianDTO.getEmail());
        }
        
        if (veterinarianRepository.findByLicenseNumber(createVeterinarianDTO.getLicenseNumber()).isPresent()) {
            throw new DuplicateResourceException("License number already exists: " + createVeterinarianDTO.getLicenseNumber());
        }
        
        Veterinarian veterinarian = veterinarianMapper.toEntity(createVeterinarianDTO);
        
        if (createVeterinarianDTO.getClinicId() != null) {
            Clinic clinic = clinicRepository.findById(createVeterinarianDTO.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + createVeterinarianDTO.getClinicId()));
            veterinarian.setClinic(clinic);
        }
        
        Veterinarian savedVeterinarian = veterinarianRepository.save(veterinarian);
        return veterinarianMapper.toDTO(savedVeterinarian);
    }
    
    @Transactional
    public VeterinarianDTO addSpecialty(Long veterinarianId, Long specialtyId) {
        log.info("Adding specialty {} to veterinarian {}", specialtyId, veterinarianId);
        
        Veterinarian veterinarian = veterinarianRepository.findByIdWithSpecialties(veterinarianId)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + veterinarianId));
        
        Specialty specialty = specialtyService.findEntityById(specialtyId);
        veterinarian.getSpecialties().add(specialty);
        
        Veterinarian savedVeterinarian = veterinarianRepository.save(veterinarian);
        return veterinarianMapper.toDTO(savedVeterinarian);
    }
    
    @Transactional
    public VeterinarianDTO removeSpecialty(Long veterinarianId, Long specialtyId) {
        log.info("Removing specialty {} from veterinarian {}", specialtyId, veterinarianId);
        
        Veterinarian veterinarian = veterinarianRepository.findByIdWithSpecialties(veterinarianId)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + veterinarianId));
        
        veterinarian.getSpecialties().removeIf(s -> s.getId().equals(specialtyId));
        
        Veterinarian savedVeterinarian = veterinarianRepository.save(veterinarian);
        return veterinarianMapper.toDTO(savedVeterinarian);
    }
    
    @Transactional
    public VeterinarianDTO update(Long id, UpdateVeterinarianDTO updateVeterinarianDTO) {
        log.info("Updating veterinarian with id: {}", id);
        
        Veterinarian existingVet = veterinarianRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
        
        if (!existingVet.getEmail().equals(updateVeterinarianDTO.getEmail()) &&
            veterinarianRepository.findByEmail(updateVeterinarianDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + updateVeterinarianDTO.getEmail());
        }
        
        veterinarianMapper.updateEntityFromDTO(updateVeterinarianDTO, existingVet);
        Veterinarian savedVeterinarian = veterinarianRepository.save(existingVet);
        return veterinarianMapper.toDTO(savedVeterinarian);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting veterinarian with id: {}", id);
        Veterinarian veterinarian = veterinarianRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
        veterinarianRepository.delete(veterinarian);
    }
    
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating veterinarian with id: {}", id);
        Veterinarian veterinarian = veterinarianRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
        veterinarian.setActive(false);
        veterinarianRepository.save(veterinarian);
    }
}
