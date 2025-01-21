package com.example.controller;

import com.example.model.Medicine;
import com.example.model.Dosage;
import com.example.repository.DosageRepository;
import org.springframework.ui.Model; //correct import for addAttribute
import com.example.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 1º
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DosageRepository dosageRepository;

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
    @GetMapping("/medicines/{id}")
    public String findById(Model model, @PathVariable Long id) {
        model.addAttribute("medicine", medicineRepository.findById(id)); //recuperar medicina por id
        return "medicine-view";
    }

////////////////////////////////////DATA CREATION////////////////////////////////////
/// //1º go to book form

    @GetMapping("/medicines/form")
    public String getForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("dosage", new Dosage());
        return "medicine-form"; //screen form
    }

    @PostMapping("/medicines")
    public String createMedicine(@ModelAttribute Medicine medicine, @ModelAttribute Dosage dosage) {
        if(medicine.getId() != null) {
            //updating
            medicineRepository.findById(medicine.getId()).ifPresent(existingMedicine -> {
                existingMedicine.setName(medicine.getName());
                existingMedicine.setDetails(medicine.getDetails());
                medicineRepository.save(existingMedicine);
            });
            dosageRepository.findById(dosage.getMedication_id()).ifPresent(existingDosage -> {
               existingDosage.setMinimum_factor(dosage.getMinimum_factor());
               existingDosage.setMaximum_factor(dosage.getMaximum_factor());
               existingDosage.setDosage_frequency(dosage.getDosage_frequency());
               dosageRepository.save(existingDosage);
            });
        } else {
            //creating
            Medicine savedMedicine = medicineRepository.save(medicine);
            dosage.setMedication_id(savedMedicine.getId());
            dosageRepository.save(dosage);
        }
        return "redirect:/medicines"; // redirects to medicines controller
    }
////////////////////////////////////DATA DELETION////////////////////////////////////

@GetMapping("/medicines/delete{id}")
    public String deleteById(@ModelAttribute Medicine medicine, @ModelAttribute Dosage dosage) {
    if(medicine.getId() != null) {
        medicineRepository.findById(medicine.getId()).ifPresent(existingMedicine -> {
            medicineRepository.deleteById(existingMedicine.getId());
        });
        dosageRepository.findById(dosage.getMedication_id()).ifPresent(existingMedicine -> {
            dosageRepository.deleteById(existingMedicine.getMedication_id());
        });
        return "redirect:/medicines/";
    } else {

    }

    return "redirect:/medicines";
}
}