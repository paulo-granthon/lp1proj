package org.openjfx.lpi.model;

import org.openjfx.lpi.controller.utils.HasName;

public class Vehicle extends Record implements HasName {
    private String model;
    private int year;

    private Vehicle (
        Integer id,
        String model,
        int year
    ) {
        super(id);
        this.model = model;
        this.year = year;
    }

    public Vehicle (String model, int year) {
        this(null, model, year);
    }

    public Vehicle (int id, String model, int year) {
        this((Integer)id, model, year);
    }

    public String getName() {
        return new StringBuilder(getModel()).append(" ").append(getYear()).toString();
    }

    public String getModel() { return model; }
    public int getYear() { return year; }

    public void setModel(String model) { this.model = model; }
    public void setYear(int ano) { this.year = ano; }
}
