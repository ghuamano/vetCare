package com.vetcare.services;

import com.vetcare.dto.*;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.Visit;
import com.vetcare.models.Pet;
import com.vetcare.models.Veterinarian;
import com.vetcare.models.Clinic;
import com.vetcare.repositories.VisitRepository;
import com.vetcare.repositories.PetRepository;
import com.vetcare.repositories.VeterinarianRepository;
import com.vetcare.repositories.ClinicRepository;
import com.vetcare.mappers.VisitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitService {

    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final ClinicRepository clinicRepository;
    private final VisitMapper visitMapper;
    
    @Transactional(readOnly = true)
    public List<VisitDTO> findAll() {
        log.debug("Finding all visits");
        List<Visit> visits = visitRepository.findAll();
        return visitMapper.toDTOList(visits);
    }
    
    @Transactional(readOnly = true)
    public VisitDTO findById(Long id) {
        log.debug("Finding visit by id: {}", id);
        Visit visit = visitRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        return visitMapper.toDTO(visit);
    }
    
    @Transactional(readOnly = true)
    public List<VisitDTO> findByPetId(Long petId) {
        log.debug("Finding visits by pet id: {}", petId);
        List<Visit> visits = visitRepository.findByPetId(petId);
        return visitMapper.toDTOList(visits);
    }
    
    @Transactional(readOnly = true)
    public List<VisitDTO> findByVeterinarianId(Long veterinarianId) {
        log.debug("Finding visits by veterinarian id: {}", veterinarianId);
        List<Visit> visits = visitRepository.findByVeterinarianId(veterinarianId);
        return visitMapper.toDTOList(visits);
    }
    
    @Transactional(readOnly = true)
    public List<VisitDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Finding visits between {} and {}", startDate, endDate);
        List<Visit> visits = visitRepository.findByVisitDateBetween(startDate, endDate);
        return visitMapper.toDTOList(visits);
    }
    
    @Transactional
    public VisitDTO create(CreateVisitDTO createVisitDTO) {
        log.info("Creating new visit for pet id: {}", createVisitDTO.getPetId());
        
        Pet pet = petRepository.findById(createVisitDTO.getPetId())
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + createVisitDTO.getPetId()));
        
        Veterinarian veterinarian = veterinarianRepository.findById(createVisitDTO.getVeterinarianId())
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + createVisitDTO.getVeterinarianId()));
        
        Clinic clinic = clinicRepository.findById(createVisitDTO.getClinicId())
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + createVisitDTO.getClinicId()));
        
        Visit visit = visitMapper.toEntity(createVisitDTO);
        visit.setPet(pet);
        visit.setVeterinarian(veterinarian);
        visit.setClinic(clinic);
        
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }
    
    @Transactional
    public VisitDTO update(Long id, UpdateVisitDTO updateVisitDTO) {
        log.info("Updating visit with id: {}", id);
        
        Visit existingVisit = visitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        
        visitMapper.updateEntityFromDTO(updateVisitDTO, existingVisit);
        Visit savedVisit = visitRepository.save(existingVisit);
        return visitMapper.toDTO(savedVisit);
    }
    
    @Transactional
    public VisitDTO updateStatus(Long id, Visit.VisitStatus status) {
        log.info("Updating visit {} status to {}", id, status);
        
        Visit visit = visitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        visit.setStatus(status);
        
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting visit with id: {}", id);
        Visit visit = visitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        visitRepository.delete(visit);
    }
}
