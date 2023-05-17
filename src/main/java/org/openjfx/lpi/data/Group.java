package org.openjfx.lpi.data;

import java.util.List;

public class Group extends Record {
    public List<Person> people;

    public Group(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPeople() { return people; }
    
}
