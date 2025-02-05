package com.example.service;

import com.example.model.Dosage;
import com.example.model.Medicine;
import com.example.model.Patient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DosageCalculatorSync {
    private BigDecimal minDosePerKg;
    private BigDecimal maxDosePerKg;
    private boolean dosesPerKgCalculated = false;

    //Calculate min Dose per KG
    public synchronized void calculateMinDosePerKg(Dosage dosage) {

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);

        if (dosage.getAvg_weight() != null) { averageWeight = dosage.getAvg_weight();
        }
        BigDecimal minimumFactor = dosage.getMinimum_factor();
        minDosePerKg = minimumFactor.divide(averageWeight, 10, BigDecimal.ROUND_HALF_UP);

        checkCompletion();
    }
    //Calculate max Dose per KG
    public synchronized void calculateMaxDosePerKg(Dosage dosage) {

        BigDecimal averageWeight = BigDecimal.valueOf(70.00);
        BigDecimal maximumFactor = dosage.getMaximum_factor();
        maxDosePerKg = maximumFactor.divide(averageWeight, 10, BigDecimal.ROUND_HALF_UP);

        checkCompletion();
    }
    private synchronized void checkCompletion() {
        if (minDosePerKg != null && maxDosePerKg != null) {
            dosesPerKgCalculated = true;
            notifyAll(); // Notify all threads that they can continue
        }
    }
    public BigDecimal minDoseAdjusted(Patient patient, Dosage dosage) {

        synchronized (this) {
            while (!dosesPerKgCalculated) {
                try {
                    wait(); // Esperar hasta que los cálculos de minDosePerKg y maxDosePerKg estén listos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());
        BigDecimal result = minDosePerKg.multiply(patientWeight);

        return result;
    }

    public BigDecimal maxDoseAdjusted(Patient patient, Dosage dosage) {

        synchronized (this) {
            while (!dosesPerKgCalculated) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        BigDecimal patientWeight = BigDecimal.valueOf(patient.getWeight());
        BigDecimal result = maxDosePerKg.multiply(patientWeight);

        return result;
    }

    public void verifyLimits(Medicine medicine, Dosage dosage, Patient patient) {

        BigDecimal minDoseAdjustedValue = minDoseAdjusted(patient, dosage);
        BigDecimal maxDoseAdjustedValue = maxDoseAdjusted(patient, dosage);
        BigDecimal maxDailyDoseValue = dosage.getMax_daily_dose();

        if (minDoseAdjustedValue.compareTo(maxDailyDoseValue) > 0) {
            throw new IllegalArgumentException("Minimum dose exceeds maximum daily dose.");
        }
        if (maxDoseAdjustedValue.compareTo(maxDailyDoseValue) > 0) {
            throw new IllegalArgumentException("Maximum dose exceeds maximum daily dose.");
        }
        if (!verifyAge(patient)) {
            throw new IllegalArgumentException("Medication " + medicine.getName() + " is not safe for patient age: " + patient.getAge());
        }
    }

    public boolean verifyAge(Patient patient) {

        int minAge = 15;
        int maxAge = 70;
        int age = patient.getAge();
        return age >= minAge && age <= maxAge;
    }

    public String calculateDosageConcurrently(Dosage dosage, Patient patient, Medicine medicine) {
        long startTime = System.nanoTime();
        System.out.println("Working with threads");

        Thread minDoseThread = new Thread(() -> calculateMinDosePerKg(dosage));
        Thread maxDoseThread = new Thread(() -> calculateMaxDosePerKg(dosage));

        minDoseThread.start();
        maxDoseThread.start();

        try {
            minDoseThread.join();
            maxDoseThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Calcular dosis ajustadas después de que terminen los hilos
        BigDecimal minDoseAdjustedVal = minDoseAdjusted(patient, dosage);
        BigDecimal maxDoseAdjustedVal = maxDoseAdjusted(patient, dosage);

        BigDecimal roundedMinDoseAdjustedVal = minDoseAdjustedVal.setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal roundedMaxDoseAdjustedVal = maxDoseAdjustedVal.setScale(3, BigDecimal.ROUND_HALF_UP);

        verifyLimits(medicine, dosage, patient);

        long endTime = System.nanoTime();
        System.out.println("Time of calculateDosageConcurrently: " + (endTime - startTime) / 1_000_000.0 + " ms");


        return "Medication is valid. Recommended dosage for " + patient.getWeight() + " kg is: " + roundedMinDoseAdjustedVal +
                ". Do NOT exceed maximum daily dose: "+ dosage.getMax_daily_dose() + " and do not exceed maximum dose: " + roundedMaxDoseAdjustedVal;
    }
}


