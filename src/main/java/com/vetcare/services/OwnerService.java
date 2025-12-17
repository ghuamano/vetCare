package com.vetcare.services;

import java.util.List;

import com.vetcare.dto.*;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.exceptions.DuplicateResourceException;
import com.vetcare.models.Owner;
import com.vetcare.repositories.OwnerRepository;
import com.vetcare.mappers.OwnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Transactional(readOnly = true)
    public List<OwnerDTO> findAll() {
        log.debug("Finding all owners");
        List<Owner> owners = ownerRepository.findAll();
        return ownerMapper.toDTOList(owners);
    }
    
    @Transactional(readOnly = true)
    public List<OwnerDTO> findAllActive() {
        log.debug("Finding all active owners");
        List<Owner> owners = ownerRepository.findByActiveTrue();
        return ownerMapper.toDTOList(owners);
    }
    
    @Transactional(readOnly = true)
    public OwnerDTO findById(Long id) {
        log.debug("Finding owner by id: {}", id);
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        return ownerMapper.toDTO(owner);
    }
    
    @Transactional(readOnly = true)
    public OwnerWithPetsDTO findByIdWithPets(Long id) {
        log.debug("Finding owner with pets by id: {}", id);
        Owner owner = ownerRepository.findByIdWithPets(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        return ownerMapper.toOwnerWithPetsDTO(owner);
    }
    
    @Transactional(readOnly = true)
    public List<OwnerDTO> searchByName(String name) {
        log.debug("Searching owners by name: {}", name);
        List<Owner> owners = ownerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        return ownerMapper.toDTOList(owners);
    }
    
    @Transactional
    public OwnerDTO create(CreateOwnerDTO createOwnerDTO) {
        log.info("Creating new owner: {} {}", createOwnerDTO.getFirstName(), createOwnerDTO.getLastName());
        
        if (createOwnerDTO.getEmail() != null && ownerRepository.findByEmail(createOwnerDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + createOwnerDTO.getEmail());
        }
        
        if (createOwnerDTO.getDocumentNumber() != null && 
            ownerRepository.findByDocumentNumber(createOwnerDTO.getDocumentNumber()).isPresent()) {
            throw new DuplicateResourceException("Document number already exists: " + createOwnerDTO.getDocumentNumber());
        }
        
        Owner owner = ownerMapper.toEntity(createOwnerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toDTO(savedOwner);
    }
    
    @Transactional
    public OwnerDTO update(Long id, UpdateOwnerDTO updateOwnerDTO) {
        log.info("Updating owner with id: {}", id);
        
        Owner existingOwner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        
        if (!existingOwner.getEmail().equals(updateOwnerDTO.getEmail()) &&
            ownerRepository.findByEmail(updateOwnerDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + updateOwnerDTO.getEmail());
        }
        
        ownerMapper.updateEntityFromDTO(updateOwnerDTO, existingOwner);
        Owner savedOwner = ownerRepository.save(existingOwner);
        return ownerMapper.toDTO(savedOwner);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting owner with id: {}", id);
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        ownerRepository.delete(owner);
    }
    
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating owner with id: {}", id);
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        owner.setActive(false);
        ownerRepository.save(owner);
    }

}
