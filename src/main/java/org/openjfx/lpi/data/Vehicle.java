package org.openjfx.lpi.data;

import org.openjfx.lpi.controller.utils.HasDisplayName;

public class Vehicle extends Record implements HasDisplayName {
    String model;
    int year;
    
    public Vehicle (
        String model,
        int year
    ) {
        this.model = model;
        this.year = year;
    }

    public String getDisplayName() {
        return new StringBuilder(getModel()).append(" ").append(getYear()).toString();
    }

    public String getModel() { return model; }
    public int getYear() { return year; }

    public void setModel(String model) { this.model = model; }
    public void setYear(int ano) { this.year = ano; }
}
