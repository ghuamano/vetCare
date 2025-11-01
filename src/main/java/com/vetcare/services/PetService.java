package com.vetcare.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetcare.exceptions.ResourceNotFoundException;
import com.vetcare.models.Owner;
import com.vetcare.models.Pet;
import com.vetcare.models.PetType;
import com.vetcare.repositories.PetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    @Transactional(readOnly = true)
    public List<Pet> findAll() {
        log.debug("Finding all pets");
        return petRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Pet> findAllActive() {
        log.debug("Finding all active pets");
        return petRepository.findByActiveTrue();
    }
    
    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        log.debug("Finding pet by id: {}", id);
        return petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public Pet findByIdWithDetails(Long id) {
        log.debug("Finding pet with details by id: {}", id);
        return petRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Pet> findByOwnerId(Long ownerId) {
        log.debug("Finding pets by owner id: {}", ownerId);
        return petRepository.findByOwnerId(ownerId);
    }
    
    @Transactional(readOnly = true)
    public List<Pet> searchByName(String name) {
        log.debug("Searching pets by name: {}", name);
        return petRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional
    public Pet create(Pet pet, Long ownerId, Long petTypeId) {
        log.info("Creating new pet: {}", pet.getName());
        
        Owner owner = ownerService.findById(ownerId);
        PetType petType = petTypeService.findById(petTypeId);
        
        pet.setOwner(owner);
        pet.setPetType(petType);
        
        return petRepository.save(pet);
    }
    
    @Transactional
    public Pet update(Long id, Pet updatedPet) {
        log.info("Updating pet with id: {}", id);
        
        Pet existingPet = findById(id);
        
        existingPet.setName(updatedPet.getName());
        existingPet.setBirthDate(updatedPet.getBirthDate());
        existingPet.setBreed(updatedPet.getBreed());
        existingPet.setGender(updatedPet.getGender());
        existingPet.setColor(updatedPet.getColor());
        existingPet.setWeight(updatedPet.getWeight());
        existingPet.setMedicalNotes(updatedPet.getMedicalNotes());
        existingPet.setPhotoUrl(updatedPet.getPhotoUrl());
        
        return petRepository.save(existingPet);
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting pet with id: {}", id);
        Pet pet = findById(id);
        petRepository.delete(pet);
    }

    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating pet with id: {}", id);
        Pet pet = findById(id);
        pet.setActive(false);
        petRepository.save(pet);
    }

}
