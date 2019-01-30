package com.mpojeda84.mapr.connectedcarmaprtofirebase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static Map<String, String> loadAllFromMapRDB() {

        initializeMapRDBConnection();

        Map<String, String> cars = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("All Cars in table");
        final DocumentStore store = connection.getStore("/obd/car-data-transformed");
        final DocumentStream stream = store.find();
         stream.forEach(x -> {
             try {
                 System.out.println("Sending object: " + x.asJsonString());
                 JsonNode node = mapper.readTree(x.asJsonString());
                 String vin = node.get("vin").asText();
                 cars.put(vin,x.asJsonString());
             } catch (IOException e) {
                 System.out.println("could not convert to car object, JSON: " + x.asJsonString());
                 e.printStackTrace();
             }
         });

        store.close();

        closeMapRDBConnection();

        return cars;
    }

}
