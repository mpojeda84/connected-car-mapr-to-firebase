package com.mpojeda84.mapr.connectedcarmaprtofirebase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.CarDataDto;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.MessageDto;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CarDataHelper {

    static DecimalFormat decimalFormat = new DecimalFormat("###.#");

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

    static CarDataDto normalizeAndFormat(CarDataDto carData) {

        carData.setOdometer(toInt(carData.getOdometer()));
        carData.setTotalFuelEconomy(prepareFuelEconomy(carData.getTotalFuelEconomy()));
        carData.setBestFuelEconomy(prepareFuelEconomy(carData.getBestFuelEconomy()));

        carData.setMilesThisWeek(toInt(carData.getMilesThisWeek()));
        carData.setMilesToday(toInt(carData.getMilesToday()));
        carData.setHighestSpeedThisWeek(roundAndFormat(carData.getHighestSpeedThisWeek()));
        carData.setHighestSpeedToday(roundAndFormat(carData.getHighestSpeedToday()));

        carData.setAvgSpeed(roundAndFormat(carData.getAvgSpeed()));
        carData.setAvgCommunitySpeed(roundAndFormat(carData.getAvgCommunitySpeed()));

        return carData;
    }

    public static String toInt(String value) {
        try {
            Double doubleValue = Double.parseDouble(value);
            return Integer.toString(doubleValue.intValue());
        }
        catch (Exception e) {System.out.println("could not convert to Double:" + value);return  value;}
    }

    public static String roundAndFormat(String value) {
        try {
            Double doubleValue = Double.parseDouble(value);
            return decimalFormat.format(doubleValue);
        }
        catch (Exception e) {System.out.println("could not convert to Double:" + value);return  value;}
    }

    private static String prepareFuelEconomy(String fuelEconomy) {
        try{return toInt(Double.toString (Double.parseDouble(fuelEconomy) / 35));}
        catch (Exception e) {System.out.println("could not convert to Double:" + fuelEconomy);return  fuelEconomy;}
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
