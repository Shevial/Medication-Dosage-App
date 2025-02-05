package com.example.service;

import com.example.model.Dosage;
import com.example.model.Medicine;
import com.example.model.Patient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DosageCalculator {

    public String calculateDosage(Patient patient, Medicine medicine, Dosage dosage) {
        if (patient == null || medicine == null || dosage == null) {
            throw new IllegalArgumentException("Patient, Medicine, and Dosage cannot be null");
        }

        try {
            System.out.println("Working without threads");
            long startTime; // for counting time

            startTime = System.nanoTime(); // start count time
            BigDecimal minDose = minDoseAdjusted(patient, dosage);
            BigDecimal maxDose = maxDoseAdjusted(patient, dosage);

            verifyLimits(medicine, dosage, patient, minDose, maxDose);
            //Rounding ONLY BEFORE printing
            BigDecimal minDoseRounded = minDose.setScale(3, RoundingMode.HALF_UP);
            BigDecimal maxDoseRounded = maxDose.setScale(3, RoundingMode.HALF_UP);

            long endTime = System.nanoTime(); // End measuring total time
            System.out.println("Total time for calculateDosage: " + (endTime - startTime) / 1_000_000.0 + " ms");

            return "Medication is valid. Recommended dosage for " + patient.getWeight() + " kg is: " + minDoseRounded +
                    ". Do NOT exceed maximum daily dose: "+ dosage.getMax_daily_dose() + " and do not exceed maximum dose: " +
                    maxDoseRounded;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
    //ESTIMATING DOSE PER KG

    //dosePerKg = minimum_factor / average weight

    public BigDecimal minDosePerKg(Dosage dosage){

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);
        BigDecimal minimumFactor = dosage.getMinimum_factor();
        BigDecimal result = minimumFactor.divide(averageWeight, 10, BigDecimal.ROUND_HALF_UP);

        return result;
    }
    public BigDecimal maxDosePerKg(Dosage dosage){

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);
        if (dosage.getAvg_weight() != null) { averageWeight = dosage.getAvg_weight();
        }
        BigDecimal maximumFactor = dosage.getMaximum_factor();
        BigDecimal result = maximumFactor.divide(averageWeight, 10, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    //ADJUSTED DOSE

    //adjustedDose = minDosePerkg * patientWeight

    public BigDecimal minDoseAdjusted(Patient patient, Dosage dosage) {

        BigDecimal minDosePerKg = minDosePerKg(dosage);
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());
        BigDecimal result = minDosePerKg.multiply(patientWeight);

        return result;
    }

    public BigDecimal maxDoseAdjusted(Patient patient, Dosage dosage) {

        BigDecimal maxDosePerKg = maxDosePerKg(dosage);
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());
        BigDecimal result = maxDosePerKg.multiply(patientWeight);

        return result;
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
    public void verifyLimits(Medicine medicine, Dosage dosage, Patient patient, BigDecimal minDoseAdjusted, BigDecimal maxDoseAdjusted) {

        BigDecimal maxDailyDose = dosage.getMax_daily_dose();

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



