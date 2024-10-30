package com.example.assessment2;

public class Student {
    private int id;
    private String name;
    private double gpa;
    private int sizeHS;

    // Constructor
    public Student(int id, String name, double gpa, int sizeHS) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.sizeHS = sizeHS;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getSizeHS() {
        return sizeHS;
    }

    public void setSizeHS(int sizeHS) {
        this.sizeHS = sizeHS;
    }
}
