package com.example.testing;

import com.example.model.Dosage;
import com.example.model.Medicine;
import com.example.model.Patient;
import com.example.service.DosageCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class DosageCalculatorTest {

    private DosageCalculator dosageCalculator;

    @BeforeEach
    void setUp() {
        dosageCalculator = new DosageCalculator();
    }

    @Test
    void testMinDosePerKg() {
        // Arrange
        Dosage dosage = new Dosage();
        dosage.setMinimum_factor(BigDecimal.valueOf(10)); // 10 mg

        // Act
        BigDecimal minDosePerKg = dosageCalculator.minDosePerKg(dosage);

        // Assert
        assertEquals(BigDecimal.valueOf(0.142857), minDosePerKg); // 10 / 70 = 0.142857
    }

    @Test
    void testMaxDosePerKg() {
        // Arrange
        Dosage dosage = new Dosage();
        dosage.setMaximum_factor(BigDecimal.valueOf(14)); // 14 mg

        // Act
        BigDecimal maxDosePerKg = dosageCalculator.maxDosePerKg(dosage);

        // Assert
        assertEquals(BigDecimal.valueOf(0.2), maxDosePerKg); // 14 / 70 = 0.2
    }

    @Test
    void testMinDoseAdjusted() {
        // Arrange
        Patient patient = new Patient(55, 80);
        Dosage dosage = new Dosage();
        dosage.setMinimum_factor(BigDecimal.valueOf(10)); // 10 mg

        // Act
        BigDecimal adjustedDose = dosageCalculator.minDoseAdjusted(patient, dosage);

        // Assert
        assertEquals(BigDecimal.valueOf(1.142857), adjustedDose); // 0.142857 * 80 = 1.142857
    }

    @Test
    void testCalculateDosageValid() {
        // Arrange
        Patient patient = new Patient(70, 60);
        Medicine medicine = new Medicine();
        medicine.setName("Aspirin");
        Dosage dosage = new Dosage();
        dosage.setMinimum_factor(BigDecimal.valueOf(10)); // 10 mg
        dosage.setMaximum_factor(BigDecimal.valueOf(20)); // 20 mg

        // Act
        String result = dosageCalculator.calculateDosage(patient, medicine, dosage);

        // Assert
        assertTrue(result.contains("Recommended dosage"));
    }

    @Test
    void testCalculateDosageInvalid() {
        // Arrange
        Patient patient = null;  // Invalid Patient
        Medicine medicine = new Medicine();
        medicine.setName("Paracetamol");
        Dosage dosage = new Dosage();
        dosage.setMinimum_factor(BigDecimal.valueOf(5)); // 5 mg

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            dosageCalculator.calculateDosage(patient, medicine, dosage);
        });
    }
}