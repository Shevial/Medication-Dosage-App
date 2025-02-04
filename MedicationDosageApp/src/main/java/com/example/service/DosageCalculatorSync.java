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
        return minDosePerKg.multiply(patientWeight);
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
        return maxDosePerKg.multiply(patientWeight);
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

        verifyLimits(medicine, dosage, patient);

        return "Min Dose Adjusted: " + minDoseAdjustedVal + " mg, Max Dose Adjusted: " + maxDoseAdjustedVal + " mg";


    }
}


