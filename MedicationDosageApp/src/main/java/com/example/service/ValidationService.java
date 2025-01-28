package com.example.service;

import com.example.model.Medicine;
import com.example.model.Dosage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationService {

    public boolean validateMedicineAndDosage(Medicine medicine, Dosage dosage, Model model) {
        // Validación del nombre de la medicina
        if (medicine.getName() == null || medicine.getName().isEmpty()) {
            model.addAttribute("error", "Name is required");
            return false;
        }

        if (!medicine.getName().matches("[a-zA-Z ]+")) {
            model.addAttribute("error", "Name must contain only letters");
            return false;
        }

        if (medicine.getName().length() < 2 || medicine.getName().length() > 100) {
            model.addAttribute("error", "Name must have between 2 and 100 characters");
            return false;
        }

        // Validación de los detalles de la medicina
        if (medicine.getDetails() != null && medicine.getDetails().length() > 200) {
            model.addAttribute("error", "Details must be under 200 characters");
            return false;
        }
       /* if(medicine.getConcentration() != null && medicine.getConcentration().doubleValue() <= 0) {
            model.addAttribute("error", "Concentration must be greater than 0");
            return false;
        }*/

        // Validación del máximo factor de dosificación
        if (dosage.getMaximum_factor() == null || dosage.getMaximum_factor().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("error", "Maximum dosage is required and must be greater than 0");
            return false;
        }

        // Validación del mínimo factor de dosificación
        if (dosage.getMinimum_factor() == null || dosage.getMinimum_factor().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("error", "Minimum dosage is required and must be greater than 0");
            return false;
        }

        // Validación de la frecuencia de dosificación
        if (dosage.getDosage_frequency() == null || dosage.getDosage_frequency().isEmpty()) {
            model.addAttribute("error", "Dosage frequency is required");
            return false;
        }

        if (dosage.getDosage_frequency().length() > 255) {
            model.addAttribute("error", "Dosage frequency must be under 255 characters");
            return false;
        }
//comment
        return true; // No errors
    }
}