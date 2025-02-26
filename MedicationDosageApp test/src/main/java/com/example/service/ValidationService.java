package com.example.service;

import com.example.model.Medicine;
import com.example.model.Dosage;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationService {

    public boolean validateMedicineAndDosage(Medicine medicine, Dosage dosage) {
        // Validación del nombre de la medicina
        if (medicine.getName() == null || medicine.getName().isEmpty()) {
            return false;
        }

        if (!medicine.getName().matches("[a-zA-Z ]+")) {
            return false;
        }

        if (medicine.getName().length() < 2 || medicine.getName().length() > 100) {
            return false;
        }

        // Validación de los detalles de la medicina
        if (medicine.getDetails() != null && medicine.getDetails().length() > 200) {
            return false;
        }

        // Validación del máximo factor de dosificación
        if (dosage.getMaximum_factor() == null || dosage.getMaximum_factor().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        // Validación del mínimo factor de dosificación
        if (dosage.getMinimum_factor() == null || dosage.getMinimum_factor().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        // Validación de la frecuencia de dosificación
        if (dosage.getDosage_frequency() == null || dosage.getDosage_frequency().isEmpty()) {
            return false;
        }

        if (dosage.getDosage_frequency().length() > 255) {
            return false;
        }
//comment
        return true; // No errors
    }
}