package org.openjfx.lpi.data;

import org.openjfx.lpi.controller.utils.HasDisplayName;

public class Place extends Record implements HasDisplayName {
    String country;
    String state;
    String city;

    public Place (
        String country,
        String state,
        String city
    ) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getDisplayName() {
        return new StringBuilder(getCity()).append(", ").append(getState()).append(" - ").append(getCountry()).toString();
    }

    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }

    public void setCountry(String pais) { this.country = pais; }
    public void setState(String estado) { this.state = estado; }
    public void setCity(String cidade) { this.city = cidade; }

}
