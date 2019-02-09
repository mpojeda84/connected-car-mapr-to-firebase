package com.mpojeda84.mapr.connectedcarmaprtofirebase.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.Application;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.CarDataDto;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.data.MapRDBDataAcess;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.MessageDto;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CarDataService {

    //@Scheduled(cron = "0 * * * * *")
    public void sendAllToFirebase(){
        System.out.println("Loding and Sending");

        //open
        MapRDBDataAcess.initializeMapRDBConnection();

        List<JsonNode> all = MapRDBDataAcess.loadAllFromMapRDB(Application.table);
        System.out.println("count:" + all.size());

        List<JsonNode> messages = MapRDBDataAcess.loadAllMessagesFromMapRDB(Application.messagesTable);
        System.out.println("messages count:" + messages.size());

        //close
        MapRDBDataAcess.closeMapRDBConnection();

        Double communityAverage = findCommunityAverage(all);
        all.stream()
                .map(CarDataHelper::process)
                .map(CarDataHelper::normalizeAndFormat)
                .map(x -> {
                    x.setAvgCommunitySpeed(CarDataHelper.toInt(communityAverage.toString()));
                    return x;
                })
                .forEach( this::sendToFirebase);

        CarDataHelper.toMessagesMap(messages).forEach(this::sendMessagesToFirebase);

    }

    private void sendMessagesToFirebase(String vin, List<MessageDto> messagesDto) {
        System.out.println("Messages for vin " + vin + ": ");
        messagesDto.stream().forEach(System.out::println);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(Application.firebase + "/messages/"+ vin +".json", CarDataHelper.serialize(messagesDto));
    }

    private void sendToFirebase(CarDataDto carDataDto) {
        System.out.println(CarDataHelper.serialize(carDataDto));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(Application.firebase + "/cars/"+ carDataDto.getVin() +".json", CarDataHelper.serialize(carDataDto));
    }

    private Double findCommunityAverage(List<JsonNode> cars) {
        Double speedTotal = cars.stream().map(x -> x.get("speedSum").asDouble(0.0)).reduce((x,y) -> x + y).orElse(Double.valueOf(0.0));
        Integer speedDataPoints = cars.stream().map(x -> x.get("dataPointCount").asInt(0)).reduce((x,y) -> x + y).orElse(0);
        return speedTotal / speedDataPoints;
    }

}
