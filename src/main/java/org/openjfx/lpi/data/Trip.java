package org.openjfx.lpi.data;

import java.util.List;

public class Trip extends Record {

    List<Person> people;
    Place route;
    Vehicle vehicle;
    
    public Trip (
        List<Person> people,
        Place lugar,
        Vehicle veiculo
    ) {
        this.people = people;
        this.route = lugar;
        this.vehicle = veiculo;
    }

    public List<Person> getPeople() { return people; }
    public Place getPlace() { return route; }
    public Vehicle getVehicle() { return vehicle; }

}
