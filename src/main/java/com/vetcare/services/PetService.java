package com.vetcare.services;

import com.vetcare.dto.*;
import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.Pet;
import com.vetcare.models.Owner;
import com.vetcare.models.PetType;
import com.vetcare.models.Clinic;
import com.vetcare.repositories.PetRepository;
import com.vetcare.repositories.OwnerRepository;
import com.vetcare.repositories.PetTypeRepository;
import com.vetcare.repositories.ClinicRepository;
import com.vetcare.mappers.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final PetTypeRepository petTypeRepository;
    private final ClinicRepository clinicRepository;
    private final PetMapper petMapper;
    
    @Transactional(readOnly = true)
    public List<PetDTO> findAll() {
        log.debug("Finding all pets");
        List<Pet> pets = petRepository.findAll();
        return petMapper.toDTOList(pets);
    }
    
    @Transactional(readOnly = true)
    public List<PetDTO> findAllActive() {
        log.debug("Finding all active pets");
        List<Pet> pets = petRepository.findByActiveTrue();
        return petMapper.toDTOList(pets);
    }
    
    @Transactional(readOnly = true)
    public PetDTO findById(Long id) {
        log.debug("Finding pet by id: {}", id);
        Pet pet = petRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        return petMapper.toDTO(pet);
    }
    
    @Transactional(readOnly = true)
    public List<PetDTO> findByOwnerId(Long ownerId) {
        log.debug("Finding pets by owner id: {}", ownerId);
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return petMapper.toDTOList(pets);
    }
    
    @Transactional(readOnly = true)
    public List<PetDTO> searchByName(String name) {
        log.debug("Searching pets by name: {}", name);
        List<Pet> pets = petRepository.findByNameContainingIgnoreCase(name);
        return petMapper.toDTOList(pets);
    }
    
    @Transactional
    public PetDTO create(CreatePetDTO createPetDTO) {
        log.info("Creating new pet: {}", createPetDTO.getName());
        
        Owner owner = ownerRepository.findById(createPetDTO.getOwnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + createPetDTO.getOwnerId()));
        
        PetType petType = petTypeRepository.findById(createPetDTO.getPetTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Pet type not found with id: " + createPetDTO.getPetTypeId()));
        
        Pet pet = petMapper.toEntity(createPetDTO);
        pet.setOwner(owner);
        pet.setPetType(petType);
        
        if (createPetDTO.getClinicId() != null) {
            Clinic clinic = clinicRepository.findById(createPetDTO.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + createPetDTO.getClinicId()));
            pet.setPrimaryClinic(clinic);
        }
        
        Pet savedPet = petRepository.save(pet);
        return petMapper.toDTO(savedPet);
    }
    
    @Transactional
    public PetDTO update(Long id, UpdatePetDTO updatePetDTO) {
        log.info("Updating pet with id: {}", id);
        
        Pet existingPet = petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        
        petMapper.updateEntityFromDTO(updatePetDTO, existingPet);
        Pet savedPet = petRepository.save(existingPet);
        return petMapper.toDTO(savedPet);
    }
    
    @Transactional
    public void delete(Long id) {
        log.info("Deleting pet with id: {}", id);
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        petRepository.delete(pet);
    }
    
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating pet with id: {}", id);
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        pet.setActive(false);
        petRepository.save(pet);
    }

}
