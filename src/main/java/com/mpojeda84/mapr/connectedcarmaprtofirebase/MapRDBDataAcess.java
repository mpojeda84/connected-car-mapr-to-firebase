package com.mpojeda84.mapr.connectedcarmaprtofirebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MapRDBDataAcess {

    private static Connection connection;

    private static void initializeMapRDBConnection() {
        connection = DriverManager.getConnection("ojai:mapr:");
        System.out.println("Connection opened");
    }

    private static void closeMapRDBConnection() {
        connection.close();
        System.out.println("Connection closed");
    }

    public static List<CarDataDto> loadAllFromMapRDB() {

        initializeMapRDBConnection();

        List<CarDataDto> list = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("All Cars in table");
        final DocumentStore store = connection.getStore("/obd/car-data-transformed");
        final DocumentStream stream = store.find();
         stream.forEach(x -> {
             try {
                 System.out.println("Sending object: " + x.asJsonString());
                 CarDataDto carDataDto = mapper.readValue(x.asJsonString(),CarDataDto.class);
                 list.add(carDataDto);
             } catch (IOException e) {
                 System.out.println("could not convert to car object, JSON: " + x.asJsonString());
                 e.printStackTrace();
             }
         });

        store.close();

        closeMapRDBConnection();

        return list;
    }

}
