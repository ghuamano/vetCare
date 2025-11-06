package com.vetcare.config;


import com.vetcare.models.*;
import com.vetcare.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner{

    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ClinicRepository clinicRepository;
    private final OwnerRepository ownerRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (petTypeRepository.count() == 0) {
            log.info("Initializing database with sample data...");
            initializePetTypes();
            initializeSpecialties();
            initializeClinics();
            initializeOwners();
            initializeVeterinarians();
            initializePets();
            initializeVisits();
            log.info("Database initialization completed!");
        } else {
            log.info("Database already contains data. Skipping initialization.");
        }
    }
    
    private void initializePetTypes() {
        List<PetType> petTypes = List.of(
            PetType.builder().name("Dog").description("Domestic canine").build(),
            PetType.builder().name("Cat").description("Domestic feline").build(),
            PetType.builder().name("Bird").description("Domestic birds").build(),
            PetType.builder().name("Rabbit").description("Domestic lagomorph").build(),
            PetType.builder().name("Hamster").description("Small rodent").build()
        );
        petTypeRepository.saveAll(petTypes);
        log.info("Created {} pet types", petTypes.size());
    }
    
    private void initializeSpecialties() {
        List<Specialty> specialties = List.of(
            Specialty.builder().name("General Medicine").description("General consultations and check-ups").build(),
            Specialty.builder().name("Surgery").description("Surgical procedures").build(),
            Specialty.builder().name("Dermatology").description("Skin diseases").build(),
            Specialty.builder().name("Cardiology").description("Heart diseases").build(),
            Specialty.builder().name("Ophthalmology").description("Eye diseases").build(),
            Specialty.builder().name("Dentistry").description("Dental health").build()
        );
        specialtyRepository.saveAll(specialties);
        log.info("Created {} specialties", specialties.size());
    }
    
    private void initializeClinics() {
        List<Clinic> clinics = List.of(
            Clinic.builder()
                .name("VetCare Central")
                .address("Av. Principal 123")
                .city("Bogotá")
                .phone("+57 1 234-5678")
                .email("central@vetcare.com")
                .workingHours("Mon-Fri: 8:00-18:00, Sat: 9:00-14:00")
                .build(),
            Clinic.builder()
                .name("VetCare North")
                .address("Calle 100 #45-67")
                .city("Bogotá")
                .phone("+57 1 987-6543")
                .email("north@vetcare.com")
                .workingHours("Mon-Fri: 7:00-19:00, Sat: 8:00-16:00")
                .build(),
            Clinic.builder()
                .name("VetCare Medellín")
                .address("Carrera 43A #14-50")
                .city("Medellín")
                .phone("+57 4 444-5555")
                .email("medellin@vetcare.com")
                .workingHours("Mon-Sat: 8:00-18:00")
                .build()
        );
        clinicRepository.saveAll(clinics);
        log.info("Created {} clinics", clinics.size());
    }
    
    private void initializeOwners() {
        List<Owner> owners = List.of(
            Owner.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .documentNumber("1234567890")
                .email("juan.perez@email.com")
                .phone("+57 300-1234567")
                .address("Calle 50 #20-30")
                .city("Bogotá")
                .build(),
            Owner.builder()
                .firstName("María")
                .lastName("García")
                .documentNumber("0987654321")
                .email("maria.garcia@email.com")
                .phone("+57 310-9876543")
                .address("Carrera 15 #80-25")
                .city("Bogotá")
                .build(),
            Owner.builder()
                .firstName("Carlos")
                .lastName("Rodríguez")
                .documentNumber("1122334455")
                .email("carlos.rodriguez@email.com")
                .phone("+57 320-5556677")
                .address("Av. El Poblado #10-90")
                .city("Medellín")
                .build()
        );
        ownerRepository.saveAll(owners);
        log.info("Created {} owners", owners.size());
    }
    
    private void initializeVeterinarians() {
        List<Clinic> clinics = clinicRepository.findAll();
        List<Specialty> specialties = specialtyRepository.findAll();
        
        Veterinarian vet1 = Veterinarian.builder()
            .firstName("Ana")
            .lastName("Martínez")
            .licenseNumber("VET-001")
            .email("ana.martinez@vetcare.com")
            .phone("+57 300-1111111")
            .clinic(clinics.get(0))
            .build();
        vet1.getSpecialties().add(specialties.get(0)); // General Medicine
        vet1.getSpecialties().add(specialties.get(1)); // Surgery
        
        Veterinarian vet2 = Veterinarian.builder()
            .firstName("Pedro")
            .lastName("Sánchez")
            .licenseNumber("VET-002")
            .email("pedro.sanchez@vetcare.com")
            .phone("+57 310-2222222")
            .clinic(clinics.get(0))
            .build();
        vet2.getSpecialties().add(specialties.get(2)); // Dermatology
        
        Veterinarian vet3 = Veterinarian.builder()
            .firstName("Laura")
            .lastName("Torres")
            .licenseNumber("VET-003")
            .email("laura.torres@vetcare.com")
            .phone("+57 320-3333333")
            .clinic(clinics.get(1))
            .build();
        vet3.getSpecialties().add(specialties.get(0)); // General Medicine
        vet3.getSpecialties().add(specialties.get(3)); // Cardiology
        
        veterinarianRepository.saveAll(List.of(vet1, vet2, vet3));
        log.info("Created {} veterinarians", 3);
    }
    
    private void initializePets() {
        List<Owner> owners = ownerRepository.findAll();
        List<PetType> petTypes = petTypeRepository.findAll();
        List<Clinic> clinics = clinicRepository.findAll();
        
        List<Pet> pets = List.of(
            Pet.builder()
                .name("Max")
                .birthDate(LocalDate.of(2020, 5, 15))
                .breed("Golden Retriever")
                .gender(Pet.Gender.MALE)
                .color("Golden")
                .weight(BigDecimal.valueOf(30.5))
                .owner(owners.get(0))
                .petType(petTypes.get(0)) // Dog
                .primaryClinic(clinics.get(0))
                .build(),
            Pet.builder()
                .name("Luna")
                .birthDate(LocalDate.of(2021, 8, 20))
                .breed("Siamese")
                .gender(Pet.Gender.FEMALE)
                .color("White and brown")
                .weight(BigDecimal.valueOf(4.2))
                .owner(owners.get(1))
                .petType(petTypes.get(1)) // Cat
                .primaryClinic(clinics.get(0))
                .build(),
            Pet.builder()
                .name("Rocky")
                .birthDate(LocalDate.of(2019, 3, 10))
                .breed("Bulldog")
                .gender(Pet.Gender.MALE)
                .color("White")
                .weight(BigDecimal.valueOf(25.0))
                .owner(owners.get(0))
                .petType(petTypes.get(0)) // Dog
                .primaryClinic(clinics.get(0))
                .build(),
            Pet.builder()
                .name("Misi")
                .birthDate(LocalDate.of(2022, 1, 5))
                .breed("Persian")
                .gender(Pet.Gender.FEMALE)
                .color("Gray")
                .weight(BigDecimal.valueOf(3.8))
                .owner(owners.get(2))
                .petType(petTypes.get(1)) // Cat
                .primaryClinic(clinics.get(2))
                .build()
        );
        petRepository.saveAll(pets);
        log.info("Created {} pets", pets.size());
    }
    
    private void initializeVisits() {
        List<Pet> pets = petRepository.findAll();
        List<Veterinarian> vets = veterinarianRepository.findAll();
        List<Clinic> clinics = clinicRepository.findAll();
        
        List<Visit> visits = List.of(
            Visit.builder()
                .visitDate(LocalDateTime.now().minusDays(7))
                .reason("General check-up")
                .diagnosis("Optimal health condition")
                .treatment("Rabies vaccination administered")
                .notes("Schedule next visit in 6 months")
                .cost(new BigDecimal("50000"))
                .status(Visit.VisitStatus.COMPLETED)
                .pet(pets.get(0))
                .veterinarian(vets.get(0))
                .clinic(clinics.get(0))
                .build(),
            Visit.builder()
                .visitDate(LocalDateTime.now().minusDays(3))
                .reason("Dermatological issues")
                .diagnosis("Allergic dermatitis")
                .treatment("Topical treatment and antihistamines")
                .notes("Follow-up in 15 days")
                .cost(new BigDecimal("75000"))
                .status(Visit.VisitStatus.COMPLETED)
                .pet(pets.get(1))
                .veterinarian(vets.get(1))
                .clinic(clinics.get(0))
                .build(),
            Visit.builder()
                .visitDate(LocalDateTime.now().plusDays(2))
                .reason("Post-operative check")
                .status(Visit.VisitStatus.SCHEDULED)
                .pet(pets.get(2))
                .veterinarian(vets.get(0))
                .clinic(clinics.get(0))
                .build(),
            Visit.builder()
                .visitDate(LocalDateTime.now().plusDays(5))
                .reason("Vaccination")
                .status(Visit.VisitStatus.SCHEDULED)
                .pet(pets.get(3))
                .veterinarian(vets.get(2))
                .clinic(clinics.get(2))
                .build()
        );
        visitRepository.saveAll(visits);
        log.info("Created {} visits", visits.size());
    }
}
