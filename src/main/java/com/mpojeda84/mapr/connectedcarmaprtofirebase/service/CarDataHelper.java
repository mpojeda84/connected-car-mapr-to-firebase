package com.mpojeda84.mapr.connectedcarmaprtofirebase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.CarDataDto;


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

    public static String serialize(CarDataDto carData) {

        ObjectMapper mapper = new ObjectMapper();
        String serialized  = null;

        try {
            serialized = mapper.writeValueAsString(carData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serialized;
    }


}
