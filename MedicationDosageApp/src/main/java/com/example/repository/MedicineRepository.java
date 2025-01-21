package com.example.repository;

import com.example.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //1º
public interface MedicineRepository extends JpaRepository<Medicine, Long> { //extends Jpa 2º
}
