package org.openjfx.lpi.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.openjfx.lpi.model.relation.Parade;
import org.openjfx.lpi.model.relation.Traveler;

public class Record {

    protected Integer id;

    protected Record(Integer id) { this.id = id; }

    public int getId() { return this.id; }
    public void setId (int id) { this.id = id; }

    public static final <T extends Record> Record create (Class<T> type, ResultSet resultSet) throws Exception {
        if (type == Person.class) return new Person(
            resultSet.getInt("id"),
            resultSet.getString("prsn_name"),
            resultSet.getString("prsn_gender"),
            resultSet.getDate("prsn_birth")
        );
        if (type == Place.class) return new Place(
            resultSet.getInt("id"),
            resultSet.getString("plce_country"),
            resultSet.getString("plce_state"),
            resultSet.getString("plce_city")
        );
        if (type == Trip.class) return new Trip(
            resultSet.getInt("id"),
            resultSet.getInt("vhcl_id")
        );
        if (type == Vehicle.class) return new Vehicle(
            resultSet.getInt("id"),
            resultSet.getString("vhcl_model"),
            resultSet.getInt("vhcl_year")
        );
        if (type == Traveler.class) return new Traveler(
            resultSet.getInt("id"),
            resultSet.getInt("trip_id"),
            resultSet.getInt("prsn_id")
        );
        if (type == Parade.class) return new Parade(
            resultSet.getInt("id"),
            resultSet.getInt("trip_id"),
            resultSet.getInt("plce_id")
        );
        throw new Exception("Record.create() -- Error: Unhandled type '" + type + "'");
    }

    public static final <T extends Record> Boolean apply (PreparedStatement statement, T record) throws Exception {
        if (record instanceof Person) {
            Person person = (Person)record;
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setDate(3, person.getBirth());
            return true;
        }
        if (record instanceof Place) {
            Place place = (Place)record;
            statement.setString(1, place.getCountry());
            statement.setString(2, place.getState());
            statement.setString(3, place.getCity());
            return true;
        }
        if (record instanceof Vehicle) {
            Vehicle vehicle = (Vehicle)record;
            statement.setString(1, vehicle.getModel());
            statement.setInt(2, vehicle.getYear());
            return true;
        }
        if (record instanceof Trip) {
            Trip trip = (Trip)record;
            statement.setInt(1, trip.getVehicleId());
            return true;
        }
        if (record instanceof Traveler) {
            Traveler traveler = (Traveler)record;
            statement.setInt(1, traveler.getTripId());
            statement.setInt(2, traveler.getPersonId());
            return true;
        }
        if (record instanceof Parade) {
            Parade parade = (Parade)record;
            statement.setInt(1, parade.getTripId());
            statement.setInt(2, parade.getplaceId());
            return true;
        }

        throw new Exception("Record.apply() -- Error: Unhandled type '" + record.getClass() + "'");
    }

}
