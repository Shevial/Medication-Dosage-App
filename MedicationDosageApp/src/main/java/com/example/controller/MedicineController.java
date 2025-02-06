/*package com.example.controller;

import com.example.model.Medicine;
import com.example.model.Dosage;
import com.example.repository.DosageRepository;
import com.example.service.ValidationService;
import jakarta.transaction.Transactional;
import org.springframework.ui.Model; //correct import for addAttribute
import com.example.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller // 1º
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;

    @Autowired
    private ValidationService validationService;

////////////////////////////////////DATA RETRIEVAL////////////////////////////////////

    @GetMapping("/")
    public String index() {
        return "redirect:/medicines";
    }

    @GetMapping("/medicines")
    public String findAllMedicines(Model model) {
        model.addAttribute("medicines", medicineRepository.findAll()); //recuperar todas las medicinas
        return "medicine-list";
    }
/// ////METER DOSAGE INFO
    @GetMapping("/medicines/view/{id}")
    public String findById(Model model, @PathVariable Long id) {
        model.addAttribute("medicine", medicineRepository.findById(id).orElse(null));
        model.addAttribute("dosage", dosageRepository.findById(id).orElse(null));
        return "medicine-view";
    }

////////////////////////////////////DATA CREATION////////////////////////////////////
/// //1º go to book form

    @GetMapping("/medicines/form")
    public String getEmptyForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("dosage", new Dosage());
        return "medicine-form"; //screen form
    }
    @GetMapping("/medicines/edit/{id}")
    public String getEditForm(Model model, @PathVariable Long id) {
        if(medicineRepository.existsById(id)){
        medicineRepository.findById(id).ifPresent(existingMedicine -> {
            model.addAttribute("medicine", medicineRepository.findById(id).orElse(null));
            model.addAttribute("dosage", dosageRepository.findById(id).orElse(null));
        });
            return "medicine-form";
        } else {
            return "redirect:/medicines/form";
        }
    }

    @PostMapping("/medicines")
    public String createMedicine(@ModelAttribute Medicine medicine,  @ModelAttribute Dosage dosage, Model model) {
        if (!validationService.validateMedicineAndDosage(medicine, dosage, model)) {
            model.addAttribute("medicine", medicine);
            model.addAttribute("dosage", dosage);
            return "medicine-form"; // Si hay errores, vuelve al formulario
        }
        if (medicine.getId() != null) {
            // Actualizando
            Medicine existingMedicine = medicineRepository.findById(medicine.getId()).orElse(null);
            if (existingMedicine != null) {
                existingMedicine.setName(medicine.getName());
                existingMedicine.setDetails(medicine.getDetails());
                //existingMedicine.setConcentration(medicine.getConcentration());
                medicineRepository.save(existingMedicine);
            }

            Dosage existingDosage = dosageRepository.findByMedicationId(medicine.getId());
            if (existingDosage != null) {
                existingDosage.setMinimum_factor(dosage.getMinimum_factor());
                existingDosage.setMaximum_factor(dosage.getMaximum_factor());
                existingDosage.setDosage_frequency(dosage.getDosage_frequency());
                existingDosage.setMax_daily_dose(dosage.getMax_daily_dose());
                existingDosage.setAvg_weight(dosage.getAvg_weight());
                dosageRepository.save(existingDosage);
            } else {
                dosage.setMedicationId(medicine.getId());
                dosageRepository.save(dosage);
            }
        } else {
            // Creando
            Medicine savedMedicine = medicineRepository.save(medicine);
            dosage.setMedicationId(savedMedicine.getId());
            dosageRepository.save(dosage);
        }
        System.out.println("Medicine saved: " + medicine);
        System.out.println("Dosage saved: " + dosage);
        return "redirect:/medicines"; // Redirige al controlador de medicinas
    }

////////////////////////////////////DATA DELETION////////////////////////////////////

@Transactional
@GetMapping("/medicines/delete/{id}")
    public String deleteById(@PathVariable Long id) {
    // Eliminar el medicamento y sus dosificaciones asociadas
    medicineRepository.findById(id).ifPresent(existingMedicine -> {
        dosageRepository.deleteBymedicationId(id); // Eliminar dosificaciones asociadas
        medicineRepository.deleteById(id); // Eliminar el medicamento
    });
    return "redirect:/medicines";
}
}*/