package com.example.model;

public class Patient {

    private final Integer age;
    private final Double weight;

    // Constructor sin setters, solo con un constructor
    public Patient(String gender, Integer age, Double weight) {

        // Validación para el campo 'age'
        if (age == null) {
            throw new IllegalArgumentException("Age cannot be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;

        // Validación para el campo 'weight'
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (weight > 500) {
            throw new IllegalArgumentException("Weight must be less than or equal to 500");
        }
        this.weight = weight;
    }




    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }
}
