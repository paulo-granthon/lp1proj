package org.openjfx.lpi.controller.utils;

import org.openjfx.lpi.model.Person;
import org.openjfx.lpi.model.Place;
import org.openjfx.lpi.model.Trip;
import org.openjfx.lpi.model.Vehicle;

public class TripWrapper {
    private Trip trip;

    private Person[] people;
    private Place[] places;
    private Vehicle vehicle;

    public TripWrapper (
        Trip trip,
        Person[] people,
        Place[] places,
        Vehicle vehicle
    ) {
        this.trip = trip;
        this.people = people;
        this.places = places;
        this.vehicle = vehicle;
    }

    public String getPeople () {
        if (people.length < 1) return "";
        StringBuilder peopleString = new StringBuilder(people[0].getName());
        for (int i = 1; i < people.length; i++) peopleString.append(people[i].getName()).append((i < people.length - 1 ? ", " : ""));
        return peopleString.toString();
    }
    public String getVehicle () {
        return vehicle.getName();
    }
    public String getPlace () {
        if (places.length < 1) return "";
        StringBuilder placesString = new StringBuilder(places[0].getName());
        for (int i = 1; i < places.length; i++) placesString.append(places[i].getName()).append((i < places.length - 1 ? ", " : ""));
        return placesString.toString();
    }

    public Trip getTrip() { return trip; }
}
