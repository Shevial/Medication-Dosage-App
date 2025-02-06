package com.example.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Entity
public class Medicine {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //private BigDecimal concentration;

    private String details;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dosage_medication_id")
    private Dosage dosage;

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }


    public Medicine() {
    }

   // @Autowired
    public Medicine(Long id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

   /* public BigDecimal getConcentration() {
        return concentration;
    }

    public void setConcentration(BigDecimal concentration) {
        this.concentration = concentration;
    }*/

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
               // ", concentration='" + concentration + '\'' +

                '}';
    }
}
