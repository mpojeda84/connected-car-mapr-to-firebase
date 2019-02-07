package com.mpojeda84.mapr.connectedcarmaprtofirebase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.CarDataDto;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.MessageDto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CarDataHelper {

    static CarDataDto process(JsonNode node) {

        CarDataDto carData = new CarDataDto();

        carData.setVin(node.get("vin").asText());
        carData.setMake(node.get("make").asText());
        carData.setYear(node.get("year").asText());
        carData.setOdometer(node.get("odometer").asText());
        carData.setTotalFuelEconomy(node.get("totalFuelEconomy").asText());
        carData.setBestFuelEconomy(node.get("bestFuelEconomy").asText());
        carData.setMilesToday(difference(node.get("maxOdometerToday").asText(), node.get("minOdometerToday").asText()));
        carData.setMilesThisWeek(difference(node.get("maxOdometerThisWeek").asText(), node.get("minOdometerThisWeek").asText()));
        carData.setHighestSpeedToday(node.get("maxSpeedToday").asText());
        carData.setHighestSpeedThisWeek(node.get("maxSpeedLast7Days").asText());
        carData.setAvgSpeed(node.get("avgSpeed").asText());
        carData.setAvgCommunitySpeed(node.get("maxSpeedLast7Days").asText());


        return carData;
    }



    private static String difference(String max, String min) {
        return Double.toString(Double.parseDouble(max) - Double.parseDouble(min));
    }

    public static String serialize(Object carData) {

        ObjectMapper mapper = new ObjectMapper();
        String serialized  = null;

        try {
            serialized = mapper.writeValueAsString(carData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    public static Map<String, List<MessageDto>> toMessagesMap(List<JsonNode> nodes) {
        Map<String, List<MessageDto>> map = new HashMap<>();
        nodes.forEach(x -> {
            map.put(x.get("vin").asText(), new LinkedList<>());
        });

        nodes.stream().forEach(x -> {
            map.get(x.get("vin").asText()).add(toMessage(x));
        });
        return map;
    }

    private static MessageDto toMessage(JsonNode node){
        MessageDto message = new MessageDto();
        message.setDate(node.get("date").asText());
        message.setMessage(node.get("message").asText());
        message.setSeverity(node.get("severity").asText());
        return message;
    }

}
