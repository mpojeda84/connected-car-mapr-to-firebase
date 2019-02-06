package com.mpojeda84.mapr.connectedcarmaprtofirebase.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ojai.DocumentStream;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

import java.io.IOException;
import java.util.*;

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

    public static List<JsonNode> loadAllFromMapRDB(String table) {

        initializeMapRDBConnection();


        List<JsonNode> cars = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("All Cars in table");
        final DocumentStore store = connection.getStore(table);
        final DocumentStream stream = store.find();

        stream.forEach(x -> {
             try {

                 JsonNode node = mapper.readTree(x.asJsonString());
                 cars.add(node);

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
