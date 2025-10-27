package com.vetcare.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.models.Clinic;
import com.vetcare.repositories.ClinicRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Transactional(readOnly = true)
    public List<Clinic> findAll() {
        log.debug("Finding all clinics");
        return clinicRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Clinic> findAllActive() {
        log.debug("Finding all active clinics");
        return clinicRepository.findByActiveTrue();
    }
    
    @Transactional(readOnly = true)
    public Clinic findById(Long id) {
        log.debug("Finding clinic by id: {}", id);
        return clinicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Clinic> findByCity(String city) {
        log.debug("Finding clinics by city: {}", city);
        return clinicRepository.findByCity(city);
    }
    
    @Transactional
    public Clinic create(Clinic clinic) {
        log.info("Creating new clinic: {}", clinic.getName());
        return clinicRepository.save(clinic);
    }
    
    @Transactional
    public Clinic update(Long id, Clinic updatedClinic) {
        log.info("Updating clinic with id: {}", id);
        
        Clinic existingClinic = findById(id);
        
        existingClinic.setName(updatedClinic.getName());
        existingClinic.setAddress(updatedClinic.getAddress());
        existingClinic.setCity(updatedClinic.getCity());
        existingClinic.setPhone(updatedClinic.getPhone());
        existingClinic.setEmail(updatedClinic.getEmail());
        existingClinic.setWorkingHours(updatedClinic.getWorkingHours());
        
        return clinicRepository.save(existingClinic);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting clinic with id: {}", id);
        Clinic clinic = findById(id);
        clinicRepository.delete(clinic);
    }
    
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating clinic with id: {}", id);
        Clinic clinic = findById(id);
        clinic.setActive(false);
        clinicRepository.save(clinic);
    }
}
