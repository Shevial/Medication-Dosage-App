package com.example.service;

import com.example.model.Dosage;
import com.example.model.Medicine;
import com.example.model.Patient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DosageCalculator {

    public String calculateDosage(Patient patient, Medicine medicine, Dosage dosage) {
        if (patient == null || medicine == null || dosage == null) {
            throw new IllegalArgumentException("Patient, Medicine, and Dosage cannot be null");
        }

        try {
            verifyLimits(medicine, dosage, patient);
            BigDecimal minDose = minDoseAdjusted(patient, dosage);
            BigDecimal maxDose = maxDoseAdjusted(patient, dosage);
            return "Medication is valid. Recommended dosage for " + patient.getWeight() + " kg is: " + minDose +
                    ". Do NOT exceed maximum daily dose: "+ dosage.getMax_daily_dose() + " and do not exceed maximum dose: " +
                    maxDose;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
    //ESTIMATING DOSE PER KG

    //dosePerKg = minimum_factor / average weight

    public BigDecimal minDosePerKg(Dosage dosage){

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);
        BigDecimal minimumFactor = dosage.getMinimum_factor();

        return minimumFactor.divide(averageWeight, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal maxDosePerKg(Dosage dosage){

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);
        BigDecimal maximumFactor = dosage.getMaximum_factor();

        return maximumFactor.divide(averageWeight, BigDecimal.ROUND_HALF_UP);
    }

    //ADJUSTED DOSE

    //adjustedDose = minDosePerkg * patientWeight

    public BigDecimal minDoseAdjusted(Patient patient, Dosage dosage) {

        BigDecimal minDosePerKg = minDosePerKg(dosage);
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());

        return minDosePerKg.multiply(patientWeight);
    }

    public BigDecimal maxDoseAdjusted(Patient patient, Dosage dosage) {

        BigDecimal maxDosePerKg = maxDosePerKg(dosage);
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());

        return maxDosePerKg.multiply(patientWeight);
    }

    public boolean verifyAge(Patient patient){
        boolean result = false;
        int minAge = 15;
        int maxAge = 70;
        int age = patient.getAge();

        if (age >= minAge && age <= maxAge) {
            result = true;
        }
        return result;
    }
    public void verifyLimits(Medicine medicine, Dosage dosage, Patient patient) {

        BigDecimal minDoseAdjusted = minDoseAdjusted(patient, dosage);
        BigDecimal maxDoseAdjusted = maxDoseAdjusted(patient, dosage);
        BigDecimal maxDailyDose = dosage.getMax_daily_dose();

        //VERIFICATION COMPARING WITH MAX DAILY DOSE
        if (minDoseAdjusted.compareTo(maxDailyDose) > 0) {
            throw new IllegalArgumentException("Minimum dose exceeds maximum daily dose.");
        }
        if (maxDoseAdjusted.compareTo(maxDailyDose) > 0) {
            throw new IllegalArgumentException("Maximum dose exceeds maximum daily dose.");
        }
        if (!verifyAge(patient)) {
            throw new IllegalArgumentException("Medication " + medicine.getName() + " is not safe for patient age: " + patient.getAge());
        }

    }

}

