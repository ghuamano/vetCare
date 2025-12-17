package com.vetcare.services;

import com.vetcare.dto.ClinicDTO;
import com.vetcare.dto.CreateClinicDTO;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.Clinic;
import com.vetcare.repositories.ClinicRepository;
import com.vetcare.mappers.ClinicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    
    @Transactional(readOnly = true)
    public List<ClinicDTO> findAll() {
        log.debug("Finding all clinics");
        List<Clinic> clinics = clinicRepository.findAll();
        return clinicMapper.toDTOList(clinics);
    }
    
    @Transactional(readOnly = true)
    public List<ClinicDTO> findAllActive() {
        log.debug("Finding all active clinics");
        List<Clinic> clinics = clinicRepository.findByActiveTrue();
        return clinicMapper.toDTOList(clinics);
    }
    
    @Transactional(readOnly = true)
    public ClinicDTO findById(Long id) {
        log.debug("Finding clinic by id: {}", id);
        Clinic clinic = clinicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
        return clinicMapper.toDTO(clinic);
    }
    
    @Transactional(readOnly = true)
    public List<ClinicDTO> findByCity(String city) {
        log.debug("Finding clinics by city: {}", city);
        List<Clinic> clinics = clinicRepository.findByCity(city);
        return clinicMapper.toDTOList(clinics);
    }
    
    @Transactional
    public ClinicDTO create(CreateClinicDTO createClinicDTO) {
        log.info("Creating new clinic: {}", createClinicDTO.getName());
        Clinic clinic = clinicMapper.toEntity(createClinicDTO);
        Clinic savedClinic = clinicRepository.save(clinic);
        return clinicMapper.toDTO(savedClinic);
    }
    
    @Transactional
    public ClinicDTO update(Long id, ClinicDTO clinicDTO) {
        log.info("Updating clinic with id: {}", id);
        
        Clinic existingClinic = clinicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
        
        clinicMapper.updateEntityFromDTO(clinicDTO, existingClinic);
        Clinic savedClinic = clinicRepository.save(existingClinic);
        return clinicMapper.toDTO(savedClinic);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting clinic with id: {}", id);
        Clinic clinic = clinicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
        clinicRepository.delete(clinic);
    }
    
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating clinic with id: {}", id);
        Clinic clinic = clinicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
        clinic.setActive(false);
        clinicRepository.save(clinic);
    }
}
