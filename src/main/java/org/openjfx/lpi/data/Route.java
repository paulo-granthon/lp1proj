package org.openjfx.lpi.data;

import java.util.List;

public class Route extends Record {
    private List<Place> places;

    public Route(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() { return places; }
    
}
