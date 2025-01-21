package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
public class Medicine {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;

    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 30, message = "Name must have between 2 and 30 characters")
    private String name;

    @Size(max = 200, message = "Details must be under 200 characters")
    private String details;

    public Medicine() {
    }

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

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
