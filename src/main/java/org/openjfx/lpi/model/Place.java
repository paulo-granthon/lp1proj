package org.openjfx.lpi.model;

import org.openjfx.lpi.controller.utils.HasName;

public class Place extends Record implements HasName {
    private String country;
    private String state;
    private String city;

    public Place (
        Integer id,
        String country,
        String state,
        String city
    ) {
        super(id);
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public Place (String country, String state, String city) {
        this(null, country, state, city);
    }

    public Place (int id, String country, String state, String city) {
        this((Integer)id, country, state, city);
    }

    public String getName() {
        return new StringBuilder(getCity()).append(", ").append(getState()).append(" - ").append(getCountry()).toString();
    }

    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }

    public void setCountry(String pais) { this.country = pais; }
    public void setState(String estado) { this.state = estado; }
    public void setCity(String cidade) { this.city = cidade; }

}
