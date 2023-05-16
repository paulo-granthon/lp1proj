package org.openjfx.lpi.data;

import java.util.List;

public class Trip {

    List<Person> people;
    Place place;
    Vehicle vehicle;
    
    public Trip (
        List<Person> pessoas,
        Place lugar,
        Vehicle veiculo
    ) {
        this.people = pessoas;
        this.place = lugar;
        this.vehicle = veiculo;
    }

    
}
