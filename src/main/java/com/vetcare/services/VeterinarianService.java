package com.vetcare.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.models.Specialty;
import com.vetcare.models.Veterinarian;
import com.vetcare.repositories.VeterinarianRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;
    private final SpecialtyService specialtyService;

    @Transactional(readOnly = true)
    public List<Veterinarian> findAll() {
        log.debug("Finding all veterinarians");
        return veterinarianRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Veterinarian> findAllActive() {
        log.debug("Finding all active veterinarians");
        return veterinarianRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Veterinarian findById(Long id) {
        log.debug("Finding veterinarian by id: {}", id);
        return veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Veterinarian findByIdWithSpecialties(Long id) {
        log.debug("Finding veterinarian with specialties by id: {}", id);
        return veterinarianRepository.findByIdWithSpecialties(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Veterinarian> findBySpecialty(Long specialtyId) {
        log.debug("Finding veterinarians by specialty id: {}", specialtyId);
        return veterinarianRepository.findBySpecialtyId(specialtyId);
    }

    @Transactional
    public Veterinarian create(Veterinarian veterinarian) {
        log.info("Creating new veterinarian: {} {}", veterinarian.getFirstName(), veterinarian.getLastName());

        if (veterinarianRepository.findByEmail(veterinarian.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + veterinarian.getEmail());
        }

        if (veterinarianRepository.findByLicenseNumber(veterinarian.getLicenseNumber()).isPresent()) {
            throw new DuplicateResourceException("License number already exists: " + veterinarian.getLicenseNumber());
        }

        return veterinarianRepository.save(veterinarian);
    }

    @Transactional
    public Veterinarian addSpecialty(Long veterinarianId, Long specialtyId) {
        log.info("Adding specialty {} to veterinarian {}", specialtyId, veterinarianId);

        Veterinarian veterinarian = findByIdWithSpecialties(veterinarianId);
        Specialty specialty = specialtyService.findById(specialtyId);

        veterinarian.getSpecialties().add(specialty);

        return veterinarianRepository.save(veterinarian);
    }

    @Transactional
    public Veterinarian removeSpecialty(Long veterinarianId, Long specialtyId) {
        log.info("Removing specialty {} from veterinarian {}", specialtyId, veterinarianId);

        Veterinarian veterinarian = findByIdWithSpecialties(veterinarianId);
        veterinarian.getSpecialties().removeIf(s -> s.getId().equals(specialtyId));

        return veterinarianRepository.save(veterinarian);
    }

    @Transactional
    public Veterinarian update(Long id, Veterinarian updatedVeterinarian) {
        log.info("Updating veterinarian with id: {}", id);

        Veterinarian existingVet = findById(id);

        if (!existingVet.getEmail().equals(updatedVeterinarian.getEmail()) &&
                veterinarianRepository.findByEmail(updatedVeterinarian.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + updatedVeterinarian.getEmail());
        }

        existingVet.setFirstName(updatedVeterinarian.getFirstName());
        existingVet.setLastName(updatedVeterinarian.getLastName());
        existingVet.setEmail(updatedVeterinarian.getEmail());
        existingVet.setPhone(updatedVeterinarian.getPhone());
        existingVet.setPhotoUrl(updatedVeterinarian.getPhotoUrl());

        return veterinarianRepository.save(existingVet);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting veterinarian with id: {}", id);
        Veterinarian veterinarian = findById(id);
        veterinarianRepository.delete(veterinarian);
    }

    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating veterinarian with id: {}", id);
        Veterinarian veterinarian = findById(id);
        veterinarian.setActive(false);
        veterinarianRepository.save(veterinarian);
    }

}
