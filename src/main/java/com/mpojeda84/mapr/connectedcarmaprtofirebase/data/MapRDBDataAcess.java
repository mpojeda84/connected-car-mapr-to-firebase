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

    public static List<JsonNode> loadAllFromMapRDB(String table) {

        List<JsonNode> cars = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Loading cars");
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
        return cars;
    }

    public static List<JsonNode> loadAllMessagesFromMapRDB(String messagesTable) {

        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> messages = new LinkedList<>();

        System.out.println("Loading cars");
        final DocumentStore store = connection.getStore(messagesTable);
        final DocumentStream stream = store. find();

        stream.forEach(x -> {
            try {
                JsonNode node = mapper.readTree(x.asJsonString());
                messages.add(node);
            } catch (IOException e) {
                System.out.println("could not convert to car object, JSON: " + x.asJsonString());
                e.printStackTrace();
            }
        });

        store.close();
        return messages;
    }

    public static void initializeMapRDBConnection() {
        connection = DriverManager.getConnection("ojai:mapr:");
        System.out.println("Connection opened");
    }

    public static void closeMapRDBConnection() {
        connection.close();
        System.out.println("Connection closed");
    }


}
