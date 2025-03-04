package com.example.controller;

import com.example.model.Medicine;
import com.example.model.Patient;
import com.example.model.Dosage;
import com.example.service.DosageCalculator;
import com.example.repository.MedicineRepository;
import com.example.repository.DosageRepository;
import com.example.service.DosageCalculatorSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/dosage")
public class RestDosageCalcController {

    @Autowired
    private DosageCalculator dosageCalculator;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;

    @Autowired
    private DosageCalculatorSync dosageCalculatorSync;

    private boolean useThreads = false; // Cambiar a false si no quieres usar hilos

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateDosage(
            @RequestParam Integer age,
            @RequestParam Integer weight,
            @RequestParam Long medicineId) {
        try {
            // Crear paciente
            Patient patient = new Patient(age, weight);

            // Obtener medicina y dosificación
            Medicine medicine = medicineRepository.findById(medicineId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
            Dosage dosage = medicine.getDosage();

            String dosageResult;

            if (useThreads) {
                // Usar la versión con hilos
                dosageResult = dosageCalculatorSync.calculateDosageConcurrently(dosage, patient, medicine);
            } else {
                // Usar la versión secuencial
                dosageResult = dosageCalculator.calculateDosage(patient, medicine, dosage);
            }

// Crear un objeto de respuesta
            DosageResponse response = new DosageResponse(dosageResult, patient, medicine, dosage);

            return ResponseEntity.ok(response); // Devuelve la respuesta en JSON

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve un error en JSON
        }
    }
// Clase interna para la respuesta
    private static class DosageResponse {
        private String dosageResult;
        private Patient patient;
        private Medicine medicine;
        private Dosage dosage;

        public DosageResponse(String dosageResult, Patient patient, Medicine medicine, Dosage dosage) {
            this.dosageResult = dosageResult;
            this.patient = patient;
            this.medicine = medicine;
            this.dosage = dosage;
        }

         public String getDosageResult() {
            return dosageResult;
        }

        public Patient getPatient() {
            return patient;
        }

        public Medicine getMedicine() {
            return medicine;
        }

        public Dosage getDosage() {
            return dosage;
        }
    }
}
