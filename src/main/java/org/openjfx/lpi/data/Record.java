package org.openjfx.lpi.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Record {

    public static final <T extends Record> Record create (Class<T> type, ResultSet resultSet) throws Exception {
        if (type == Person.class) return new Person(
            resultSet.getString("prsn_name"),
            resultSet.getString("prsn_gender"),
            resultSet.getDate("prsn_birth")
        );
        if (type == Place.class) return new Place(
            resultSet.getString("plce_country"),
            resultSet.getString("plce_state"),
            resultSet.getString("plce_city")
        );
        if (type == Vehicle.class) return new Vehicle(
            resultSet.getString("vhcl_model"),
            resultSet.getInt("vhcl_year")
        );
        throw new Exception("Record.create() -- Error: Unhandled type '" + type + "'");
    }

    public static final <T extends Record> void apply (PreparedStatement statement, T record) throws Exception {
        if (record instanceof Person) {
            Person person = (Person)record;
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setDate(3, person.getBirth());
            return;
        }
        if (record instanceof Place) {
            Place place = (Place)record;
            statement.setString(1, place.getCountry());
            statement.setString(2, place.getState());
            statement.setString(3, place.getCity());
            return;
        }
        if (record instanceof Vehicle) {
            Vehicle vehicle = (Vehicle)record;
            statement.setString(1, vehicle.getModel());
            statement.setInt(2, vehicle.getYear());
            return;
        }
        // throw new Exception("Record.apply() -- Error: Unhandled type '" + type + "'");
    }

}
