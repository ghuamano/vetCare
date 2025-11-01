package com.vetcare.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.vetcare.models.Pet;
import com.vetcare.services.PetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets(){
        return ResponseEntity.ok(petService.findAll());
    }

}
