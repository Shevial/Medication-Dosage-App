package com.example.controller;

import com.example.model.Medicine;
import com.example.model.Patient;
import com.example.model.Dosage;
import com.example.service.DosageCalculator;
import com.example.repository.MedicineRepository;
import com.example.repository.DosageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DosageCalcController {

    @Autowired
    private DosageCalculator dosageCalculator;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;

    @GetMapping("/patientform")
    public String showPatientForm(Model model) {
        model.addAttribute("patient", new Patient(0, 0)); // Inicializa con valores por defecto
        return "patient-form"; // Nombre de la vista del formulario
    }

    @PostMapping("/calculateDosage")
    public String calculateDosage(@RequestParam Integer age,
                                  @RequestParam Integer weight,
                                  @RequestParam Long medicineId,
                                  Model model) {
        try {
            // Crear paciente
            Patient patient = new Patient(age, weight);

            // Obtener medicina y dosificación
            Medicine medicine = medicineRepository.findById(medicineId)
                    .orElseThrow(() -> new IllegalArgumentException("Medicine not found"));
            Dosage dosage = dosageRepository.findByMedicationId(medicineId);

            // Calcular dosis
            String dosageResult = dosageCalculator.calculateDosage(patient, medicine, dosage);

            // Agregar resultados al modelo
            model.addAttribute("dosageResult", dosageResult);
            model.addAttribute("patient", patient);
            model.addAttribute("medicine", medicine);
            model.addAttribute("dosage", dosage);

            return "dosage-result"; // Nombre de la vista de resultados

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Nombre de la vista de error
        }
    }
}