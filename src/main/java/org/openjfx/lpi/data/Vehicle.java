package org.openjfx.lpi.data;

public class Vehicle {
    String model;
    int year;
    
    public Vehicle (
        String model,
        int year
    ) {
        this.model = model;
        this.year = year;
    }

    public String getModel() { return model; }
    public int getYear() { return year; }

    public void setModel(String model) { this.model = model; }
    public void setYear(int ano) { this.year = ano; }
}
