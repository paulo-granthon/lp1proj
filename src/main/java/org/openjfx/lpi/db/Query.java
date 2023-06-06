package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openjfx.lpi.controller.utils.TripWrapper;
import org.openjfx.lpi.model.Person;
import org.openjfx.lpi.model.Place;
import org.openjfx.lpi.model.Record;
import org.openjfx.lpi.model.Trip;
import org.openjfx.lpi.model.Vehicle;
import org.openjfx.lpi.model.relation.Parade;
import org.openjfx.lpi.model.relation.Traveler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Query {

    public static Optional<Connection> connect () {
        return Optional.ofNullable(SQLConnection.connect());
    }

    public static boolean close (Optional<Connection> connectionOptional) {
        try {
            return close(connectionOptional.orElseThrow(
                () -> new Exception("Query.close() -- Erro: Connexão inválida")
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean close (Connection connection) throws Exception {
        try {
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T extends Record> Optional<Integer> insert (Table table, Class<T> type, T record, Optional<Connection> connectionOptional) {
        try {
            ObjectProperty<Boolean> commitAndClose = new SimpleObjectProperty<Boolean>(false);
            Connection connection = connectionOptional.orElseGet(() -> {
                commitAndClose.set(true);
                return SQLConnection.connect();
            });
            PreparedStatement statement = connection.prepareStatement(
                new StringBuilder(table.getInsertQuery())
                .append(" RETURNING id")
                .toString()
            );

            Record.<T>apply(statement, record);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);

            if (commitAndClose.get()) close(connection);

            return Optional.of(id);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Integer> insertPerson (Person person) { return insertPerson(person, Optional.empty()); }
    public static Optional<Integer> insertPerson (Person person, Optional<Connection> connectionOptional) {
        return Query.<Person>insert(Table.Person, Person.class, person, connectionOptional);
    }

    public static Optional<Integer> insertPlace (Place place) { return insertPlace(place, Optional.empty()); }
    public static Optional<Integer> insertPlace (Place place, Optional<Connection> connectionOptional) {
        return Query.<Place>insert(Table.Place, Place.class, place, connectionOptional);
    }

    public static Optional<Integer> insertVehicle (Vehicle vehicle) { return insertVehicle(vehicle, Optional.empty()); }
    public static Optional<Integer> insertVehicle (Vehicle vehicle, Optional<Connection> connectionOptional) {
        return Query.<Vehicle>insert(Table.Vehicle, Vehicle.class, vehicle, connectionOptional);
    }

    public static Optional<Integer> insertTraveler (Traveler traveler) { return insertTraveler(traveler, Optional.empty()); }
    public static Optional<Integer> insertTraveler (Traveler traveler, Optional<Connection> connectionOptional) {
        return Query.<Traveler>insert(Table.Traveler, Traveler.class, traveler, connectionOptional);
    }

    public static Optional<Integer> insertParade (Parade parade) { return insertParade(parade, Optional.empty()); }
    public static Optional<Integer> insertParade (Parade parade, Optional<Connection> connectionOptional) {
        return Query.<Parade>insert(Table.Parade, Parade.class, parade, connectionOptional);
    }

    public static Optional<Integer> insertTrip (Trip trip) { return insertTrip(trip, Optional.empty()); }
    public static Optional<Integer> insertTrip (Trip trip, Optional<Connection> connectionOptional) {
        System.out.println("adding new Trip");
        Optional<Integer> result =  Query.<Trip>insert(Table.Trip, Trip.class, trip, connectionOptional);
        System.out.println("result is " + result.orElse(null));
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Record> T[] select (
        Table table,
        Class<T> type,
        Optional<String> query,
        Optional<Connection> connectionOptional
    ) {
        try {
            ObjectProperty<Boolean> commitAndClose = new SimpleObjectProperty<Boolean>(false);
            Connection connection = connectionOptional.orElseGet(() -> {
                commitAndClose.set(true);
                return SQLConnection.connect();
            });
            ResultSet resultSet = connection.prepareStatement(
                table.getSelectQuery().append(" ").append(query.orElse("")).toString()
            ).executeQuery();

            ArrayList<T> resultList = new ArrayList<>();

            while (resultSet.next()) resultList.add((T)Record.<T>create(type, resultSet));

            if (commitAndClose.get()) close(connection);

            return resultList.toArray((T[])Array.newInstance(type, resultList.size()));

        } catch (Exception ex) {
            System.out.println("Query.selectAll() -- Erro: Nenhum ResultSet retornado para a query");
            ex.printStackTrace();
            return (T[])new Record[0];
        }
    }

    public static Traveler[] selectTravelersOfTrip(int tripId, Optional<Connection> connectionOptional) {
        return select(
            Table.Traveler,
            Traveler.class,
            Optional.of("WHERE trip_id = " + tripId),
            connectionOptional
        );
    }
    public static Parade[] selectParadesOfTrip(int tripId, Optional<Connection> connectionOptional) {
        return select(
            Table.Parade,
            Parade.class,
            Optional.of("WHERE trip_id = " + tripId),
            connectionOptional
        );
    }

    public static <T extends Record> T[] selectAll (Table table, Class<T> type, Optional<Connection> connectionOptional) {
        return select(table, type, Optional.empty(), connectionOptional);
    }

    public static Person[] selectAllPerson (Optional<Connection> connectionOptional) {
        return Query.<Person>selectAll(Table.Person, Person.class, connectionOptional);
    }
    public static Place[] selectAllPlace (Optional<Connection> connectionOptional) {
        return Query.<Place>selectAll(Table.Place, Place.class, connectionOptional);
    }
    public static Vehicle[] selectAllVehicle (Optional<Connection> connectionOptional) {
        return Query.<Vehicle>selectAll(Table.Vehicle, Vehicle.class, connectionOptional);
    }
    public static TripWrapper[] selectAllTrip (
        List<Person> loadedPeople,
        List<Place> loadedPlaces,
        Vehicle[] loadedVehicles,
        Optional<Connection> connectionOptional
    ) {
        return Arrays.asList(Query.<Trip>selectAll(Table.Trip, Trip.class, connectionOptional)).stream().map((Trip trip) -> {
            Vehicle vehicle = null;
            for (Vehicle loadedVehicle : loadedVehicles) {
                if (loadedVehicle.getId() != trip.getVehicleId()) continue;
                vehicle = loadedVehicle;
                break;
            }
            return new TripWrapper(
                trip,
                Arrays.asList(selectTravelersOfTrip(trip.getId(), connectionOptional)).stream()
                .map((Traveler traveler) -> {
                    for (Person itemMatch : loadedPeople) if (traveler.getId() == itemMatch.getId()) return itemMatch;
                    return null;
                })
                .filter(item -> item != null)
                .toArray(Person[]::new),
                Arrays.asList(selectParadesOfTrip(trip.getId(), connectionOptional)).stream()
                .map((Parade parade) -> {
                    for (Place itemMatch : loadedPlaces) if (parade.getId() == itemMatch.getId()) return itemMatch;
                    return null;
                })
                .filter(item -> item != null)
                .toArray(Place[]::new),
                vehicle
            );
        }).toArray(TripWrapper[]::new);
    }

    public static Traveler[] selectAllTraveler (Optional<Connection> connectionOptional) {
        return Query.<Traveler>selectAll(Table.Traveler, Traveler.class, connectionOptional);
    }

    public static Parade[] selectAllParade (Optional<Connection> connectionOptional) {
        return Query.<Parade>selectAll(Table.Parade, Parade.class, connectionOptional);
    }


    public static void executeSqlFile (String file_path) {
        try {
            Connection connection = SQLConnection.connect();
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append(System.lineSeparator());
            }
            connection.createStatement().execute(sb.toString());
            br.close();
            close(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
