package com.example.controller;

import com.example.model.Dosage;
import com.example.model.Medicine;
import com.example.repository.DosageRepository;
import com.example.service.ValidationService;
import jakarta.transaction.Transactional;
import com.example.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
public class RestControllerMedicine {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;


    @PostMapping("/")
    public ResponseEntity<?> createMedicine(@Valid @RequestBody Medicine medicine) {
        // Save the Dosage entity first
        if (medicine.getDosage() != null) {
            dosageRepository.save(medicine.getDosage());
        }

        // Save the Medicine entity
        Medicine savedMedicine = medicineRepository.save(medicine);

        return ResponseEntity.ok(savedMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedicine(@PathVariable Long id, @Valid @RequestBody Medicine medicine) {
        Optional<Medicine> existingMedicineOpt = medicineRepository.findById(id);
        if (existingMedicineOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Medicine existingMedicine = existingMedicineOpt.get();

        // Update the Dosage entity
        if (medicine.getDosage() != null) {
            Dosage existingDosage = existingMedicine.getDosage();
            if (existingDosage != null) {
                // Update existing Dosage
                existingDosage.setMaximum_factor(medicine.getDosage().getMaximum_factor());
                existingDosage.setMinimum_factor(medicine.getDosage().getMinimum_factor());
                existingDosage.setDosage_frequency(medicine.getDosage().getDosage_frequency());
                existingDosage.setMax_daily_dose(medicine.getDosage().getMax_daily_dose());
                existingDosage.setAvg_weight(medicine.getDosage().getAvg_weight());
                dosageRepository.save(existingDosage);
            } else {
                // Save new Dosage
                dosageRepository.save(medicine.getDosage());
            }
        }

        // Update the Medicine entity
        existingMedicine.setName(medicine.getName());
        existingMedicine.setDetails(medicine.getDetails());
        existingMedicine.setDosage(medicine.getDosage());
        medicineRepository.save(existingMedicine);

        return ResponseEntity.ok(existingMedicine);
    }
}