package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.math.BigDecimal;

@Entity
public class Dosage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long medicationId;

    public BigDecimal maximum_factor;

    public BigDecimal minimum_factor;

    public String dosage_frequency;

    public BigDecimal max_daily_dose;

    public BigDecimal avg_weight;

    public Dosage() {
    }

    public Dosage(Long medicationId, BigDecimal maximum_factor, BigDecimal minimum_factor, String dosage_frequency, BigDecimal max_daily_dose, BigDecimal avg_weight) {
        this.medicationId = medicationId;
        this.maximum_factor = maximum_factor;
        this.minimum_factor = minimum_factor;
        this.dosage_frequency = dosage_frequency;
        this.max_daily_dose = max_daily_dose;
        this.avg_weight = avg_weight;

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

    public BigDecimal getMax_daily_dose() {return max_daily_dose;}

    public void setMax_daily_dose(BigDecimal max_daily_dose) {this.max_daily_dose = max_daily_dose;}

    public BigDecimal getAvg_weight() {return avg_weight;}

    public void setAvg_weight(BigDecimal avg_weight) {this.avg_weight = avg_weight;}

    @Override
    public String toString() {
        return "Dosage{" +
                "medicationId=" + medicationId +
                ", maximum_factor=" + maximum_factor +
                ", minimum_factor=" + minimum_factor +
                ", dosage_frequency='" + dosage_frequency + '\'' +
                ", max_daily_dose=" + max_daily_dose +
                ", avg_weight=" + avg_weight +
                '}';
    }
}

