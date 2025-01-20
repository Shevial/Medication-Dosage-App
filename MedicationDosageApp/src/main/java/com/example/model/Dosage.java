package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Dosage {
    @Id
    public Long medication_id;

    public BigDecimal maximum_factor;

    public BigDecimal minimum_factor;

    public String dosage_frequency;


    public Dosage() {
    }

    public Dosage(Long medication_id, BigDecimal maximum_factor, BigDecimal minimum_factor, String dosage_frequency) {
        this.medication_id = medication_id;
        this.maximum_factor = maximum_factor;
        this.minimum_factor = minimum_factor;
        this.dosage_frequency = dosage_frequency;
    }

    public Long getMedication_id() {
        return medication_id;
    }

    public void setMedication_id(Long medication_id) {
        this.medication_id = medication_id;
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
                "medication_id=" + medication_id +
                ", maximum_factor=" + maximum_factor +
                ", minimum_factor=" + minimum_factor +
                ", dosage_frequency='" + dosage_frequency + '\'' +
                '}';
    }
}

