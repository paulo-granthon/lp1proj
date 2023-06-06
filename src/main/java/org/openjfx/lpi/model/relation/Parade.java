package org.openjfx.lpi.model.relation;
import org.openjfx.lpi.model.Record;

public class Parade extends Record {

    private int tripId;
    private int placeId;

    public Parade(
        Integer id,    
        int tripId,
        int placeId
    ) {
        super(id);
        this.tripId = tripId;
        this.placeId = placeId;
    }

    public Parade(int tripId, int placeId) {
        this(null, tripId, placeId);
    }

    public Parade(int id, int tripId, int placeId) {
        this((Integer)id, tripId, placeId);
    }

    public int getTripId() { return tripId; }
    public int getplaceId() { return placeId; }

}
