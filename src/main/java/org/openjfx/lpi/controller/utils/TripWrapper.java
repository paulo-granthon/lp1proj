package org.openjfx.lpi.controller.utils;

import java.util.stream.Collectors;

import org.openjfx.lpi.data.Place;
import org.openjfx.lpi.data.Trip;
import org.openjfx.lpi.data.Vehicle;

public class TripWrapper {
    private Trip trip;
    public TripWrapper (Trip trip) {
        this.trip = trip;
    }
    public String[] getPeople () {
        return trip.getPeople().stream().map(person -> person.getName()).collect(Collectors.toList()).toArray(String[]::new);
    }
    public String getVehicle () {
        Vehicle vehicle = trip.getVehicle();
        return vehicle.getModel() + " " + vehicle.getYear();
    }
    public String getPlace () {
        Place place = trip.getPlace();
        return place.getCity() + ", " + place.getState() + " - " + place.getCountry();
    }
}
