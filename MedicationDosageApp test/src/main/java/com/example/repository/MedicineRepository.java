package com.example.repository;

import com.example.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //1º
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> getMedicinesById(Long id); //extends Jpa 2º
}
