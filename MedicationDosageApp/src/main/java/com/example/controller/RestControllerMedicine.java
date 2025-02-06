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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
public class RestControllerMedicine {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;

    @PostMapping("/form")
    public ResponseEntity<?> createMedicine(@Valid @RequestBody Medicine medicine) {
        if (medicine.getDosage() != null) {
            dosageRepository.save(medicine.getDosage());
        }
        Medicine savedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(savedMedicine);
    }

    @PutMapping("/form/{id}")
    public ResponseEntity<?> updateMedicine(@PathVariable Long id, @Valid @RequestBody Medicine medicine) {
        Optional<Medicine> existingMedicineOpt = medicineRepository.findById(id);
        if (existingMedicineOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Medicine existingMedicine = existingMedicineOpt.get();

        if (medicine.getDosage() != null) {
            Dosage existingDosage = existingMedicine.getDosage();
            if (existingDosage != null) {
                existingDosage.setMaximum_factor(medicine.getDosage().getMaximum_factor());
                existingDosage.setMinimum_factor(medicine.getDosage().getMinimum_factor());
                existingDosage.setDosage_frequency(medicine.getDosage().getDosage_frequency());
                existingDosage.setMax_daily_dose(medicine.getDosage().getMax_daily_dose());
                existingDosage.setAvg_weight(medicine.getDosage().getAvg_weight());
                dosageRepository.save(existingDosage);
            } else {
                dosageRepository.save(medicine.getDosage());
            }
        }

        existingMedicine.setName(medicine.getName());
        existingMedicine.setDetails(medicine.getDetails());
        existingMedicine.setDosage(medicine.getDosage());
        medicineRepository.save(existingMedicine);

        return ResponseEntity.ok(existingMedicine);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if (medicine.isPresent()) {
            return ResponseEntity.ok(medicine.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if (medicine.isPresent()) {
            if (medicine.get().getDosage() != null) {
                dosageRepository.delete(medicine.get().getDosage());
            }
            medicineRepository.delete(medicine.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}