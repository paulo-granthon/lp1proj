package org.openjfx.lpi.data;

public class Place extends Record {
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

    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }

    public void setCountry(String pais) { this.country = pais; }
    public void setState(String estado) { this.state = estado; }
    public void setCity(String cidade) { this.city = cidade; }

}
