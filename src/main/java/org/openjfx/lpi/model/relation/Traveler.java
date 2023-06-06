package org.openjfx.lpi.model.relation;
import org.openjfx.lpi.model.Record;

public class Traveler extends Record {

    private int tripId;
    private int personId;

    public Traveler(
        Integer id,    
        int tripId,
        int personId
    ) {
        super(id);
        this.tripId = tripId;
        this.personId = personId;
    }

    public Traveler(int tripId, int personId) {
        this(null, tripId, personId);
    }

    public Traveler(int id, int tripId, int personId) {
        this((Integer)id, tripId, personId);
    }

    public int getTripId() { return tripId; }
    public int getPersonId() { return personId; }

}
