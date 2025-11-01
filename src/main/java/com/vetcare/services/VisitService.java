package com.vetcare.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.Clinic;
import com.vetcare.models.Pet;
import com.vetcare.models.Veterinarian;
import com.vetcare.models.Visit;
import com.vetcare.repositories.VisitRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitService {

    private final VisitRepository visitRepository;
    private final PetService petService;
    private final VeterinarianService veterinarianService;
    private final ClinicService clinicService;

    @Transactional(readOnly = true)
    public List<Visit> findAll() {
        log.debug("Finding all visits");
        return visitRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Visit findById(Long id) {
        log.debug("Finding visit by id: {}", id);
        return visitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public Visit findByIdWithDetails(Long id) {
        log.debug("Finding visit with details by id: {}", id);
        return visitRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Visit> findByPetId(Long petId) {
        log.debug("Finding visits by pet id: {}", petId);
        return visitRepository.findByPetId(petId);
    }
    
    @Transactional(readOnly = true)
    public List<Visit> findByVeterinarianId(Long veterinarianId) {
        log.debug("Finding visits by veterinarian id: {}", veterinarianId);
        return visitRepository.findByVeterinarianId(veterinarianId);
    }
    
    @Transactional(readOnly = true)
    public List<Visit> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Finding visits between {} and {}", startDate, endDate);
        return visitRepository.findByVisitDateBetween(startDate, endDate);
    }
    
    @Transactional
    public Visit create(Visit visit, Long petId, Long veterinarianId, Long clinicId) {
        log.info("Creating new visit for pet id: {}", petId);
        
        Pet pet = petService.findById(petId);
        Veterinarian veterinarian = veterinarianService.findById(veterinarianId);
        Clinic clinic = clinicService.findById(clinicId);
        
        visit.setPet(pet);
        visit.setVeterinarian(veterinarian);
        visit.setClinic(clinic);
        
        return visitRepository.save(visit);
    }
    
    @Transactional
    public Visit update(Long id, Visit updatedVisit) {
        log.info("Updating visit with id: {}", id);
        
        Visit existingVisit = findById(id);
        
        existingVisit.setVisitDate(updatedVisit.getVisitDate());
        existingVisit.setReason(updatedVisit.getReason());
        existingVisit.setDiagnosis(updatedVisit.getDiagnosis());
        existingVisit.setTreatment(updatedVisit.getTreatment());
        existingVisit.setNotes(updatedVisit.getNotes());
        existingVisit.setCost(updatedVisit.getCost());
        existingVisit.setStatus(updatedVisit.getStatus());
        
        return visitRepository.save(existingVisit);
    }
    
    @Transactional
    public Visit updateStatus(Long id, Visit.VisitStatus status) {
        log.info("Updating visit {} status to {}", id, status);
        
        Visit visit = findById(id);
        visit.setStatus(status);
        
        return visitRepository.save(visit);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting visit with id: {}", id);
        Visit visit = findById(id);
        visitRepository.delete(visit);
    }

    

}
