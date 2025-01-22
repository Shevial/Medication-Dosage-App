package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Dosage {
    @Id
    public Long medicationId;

    @NotNull(message = "Maximum dosage is required")
    @Digits(integer = 10, fraction = 2, message = "Maximum factor must be a number with up to 10 digits and 2 decimal places")
    public BigDecimal maximum_factor;

    @NotNull(message = "Minimum dosage is required")
    @Digits(integer = 10, fraction = 2, message = "Minimum factor must be a number with up to 10 digits and 2 decimal places")
    public BigDecimal minimum_factor;

    public String dosage_frequency;


    public Dosage() {
    }

    public Dosage(Long medicationId, BigDecimal maximum_factor, BigDecimal minimum_factor, String dosage_frequency) {
        this.medicationId = medicationId;
        this.maximum_factor = maximum_factor;
        this.minimum_factor = minimum_factor;
        this.dosage_frequency = dosage_frequency;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medication_id) {
        this.medicationId = medication_id;
    }

    public BigDecimal getMaximum_factor() {
        return maximum_factor;
    }

    public void setMaximum_factor(BigDecimal maximum_factor) {
        this.maximum_factor = maximum_factor;
    }

    public BigDecimal getMinimum_factor() {
        return minimum_factor;
    }

    public void setMinimum_factor(BigDecimal minimum_factor) {
        this.minimum_factor = minimum_factor;
    }

    public String getDosage_frequency() {
        return dosage_frequency;
    }

    public void setDosage_frequency(String dosage_frequency) {
        this.dosage_frequency = dosage_frequency;
    }

    @Override
    public String toString() {
        return "Dosage{" +
                "medication_id=" + medicationId +
                ", maximum_factor=" + maximum_factor +
                ", minimum_factor=" + minimum_factor +
                ", dosage_frequency='" + dosage_frequency + '\'' +
                '}';
    }

}

