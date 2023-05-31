package org.openjfx.lpi.data;

import java.util.List;

public class Trip extends Record {

    List<Person> people;
    List<Place> place;
    Vehicle vehicle;
    
    public Trip (
        List<Person> people,
        List<Place> place,
        Vehicle vehicle
    ) {
        this.people = people;
        this.place = place;
        this.vehicle = vehicle;
    }

    public List<Person> getPeople() { return people; }
    public List<Place> getPlace() { return place; }
    public Vehicle getVehicle() { return vehicle; }

}
