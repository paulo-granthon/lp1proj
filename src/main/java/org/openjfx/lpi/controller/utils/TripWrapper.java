package org.openjfx.lpi.controller.utils;

import java.util.stream.Collectors;

import org.openjfx.lpi.data.Trip;

public class TripWrapper {
    private Trip trip;
    public TripWrapper (Trip trip) {
        this.trip = trip;
    }
    public String[] getPeople () {
        return trip.getPeople().stream().map(
            person -> person.getName()
        ).collect(Collectors.toList()).toArray(String[]::new);
    }
    public String getVehicle () {
        return trip.getVehicle().getDisplayName();
    }
    public String[] getPlace () {
        return trip.getPlace().stream().map(
            place -> place.getDisplayName()
        ).collect(Collectors.toList()).toArray(String[]::new);
    }
}
