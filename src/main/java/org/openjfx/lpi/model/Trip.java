package org.openjfx.lpi.model;

public class Trip extends Record {
    private int vehicleId;
    private Trip (Integer id, int vehicleId) { 
        super(id);
        this.vehicleId = vehicleId;
    }
    public Trip (int vehicleId) { this(null, vehicleId); }
    public Trip (int id, int vehicleId) { this((Integer)id, vehicleId); }

    public int getVehicleId() { return vehicleId; }
}
