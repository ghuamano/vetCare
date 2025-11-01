package com.vetcare.services;

import java.util.List;

import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.exceptions.DuplicateResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.models.Owner;
import com.vetcare.repositories.OwnerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    public List<Owner> findAll() {
        log.debug("Finding all owners");
        return ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Owner> findAllActive() {
        log.debug("Finding all active owners");
        return ownerRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Owner findById(Long id) {
        log.debug("Finding owner by id: {}", id);
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Owner findByIdWithPets(Long id) {
        log.debug("Finding owner with pets by id: {}", id);
        return ownerRepository.findByIdWithPets(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Owner> searchByName(String name) {
        log.debug("Searching owners by name: {}", name);
        return ownerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    @Transactional
    public Owner create(Owner owner) {
        log.info("Creating new owner: {} {}", owner.getFirstName(), owner.getLastName());

        if (owner.getEmail() != null && ownerRepository.findByEmail(owner.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + owner.getEmail());
        }

        if (owner.getDocumentNumber() != null &&
                ownerRepository.findByDocumentNumber(owner.getDocumentNumber()).isPresent()) {
            throw new DuplicateResourceException("Document number already exists: " + owner.getDocumentNumber());
        }

        return ownerRepository.save(owner);
    }

    @Transactional
    public Owner update(Long id, Owner updatedOwner) {
        log.info("Updating owner with id: {}", id);

        Owner existingOwner = findById(id);

        if (!existingOwner.getEmail().equals(updatedOwner.getEmail()) &&
                ownerRepository.findByEmail(updatedOwner.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + updatedOwner.getEmail());
        }

        existingOwner.setFirstName(updatedOwner.getFirstName());
        existingOwner.setLastName(updatedOwner.getLastName());
        existingOwner.setEmail(updatedOwner.getEmail());
        existingOwner.setPhone(updatedOwner.getPhone());
        existingOwner.setAddress(updatedOwner.getAddress());
        existingOwner.setCity(updatedOwner.getCity());
        existingOwner.setDocumentNumber(updatedOwner.getDocumentNumber());

        return ownerRepository.save(existingOwner);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting owner with id: {}", id);
        Owner owner = findById(id);
        ownerRepository.delete(owner);
    }

    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating owner with id: {}", id);
        Owner owner = findById(id);
        owner.setActive(false);
        ownerRepository.save(owner);
    }

}
