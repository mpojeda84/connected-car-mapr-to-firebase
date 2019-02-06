package com.mpojeda84.mapr.connectedcarmaprtofirebase.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.Application;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.model.CarDataDto;
import com.mpojeda84.mapr.connectedcarmaprtofirebase.data.MapRDBDataAcess;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CarDataService {

    //@Scheduled(cron = "0 * * * * *")
    public void sendAllToFirebase(){
        System.out.println("Loding and Sending");

        List<JsonNode> all = MapRDBDataAcess.loadAllFromMapRDB(Application.table);
        System.out.println("count:" + all.size());

        Double communityAverage = findCommunityAverage(all);
        all.stream()
                .map(CarDataHelper::process)
                .map(x -> {
                    x.setAvgCommunitySpeed(communityAverage.toString());
                    return x;
                })
                .forEach( this::sendToFirebase);
    }

    private Double findCommunityAverage(List<JsonNode> cars) {
        Double speedTotal = cars.stream().map(x -> x.get("speedSum").asDouble(0.0)).reduce((x,y) -> x + y).orElse(Double.valueOf(0.0));
        Integer speedDataPoints = cars.stream().map(x -> x.get("dataPointCount").asInt(0)).reduce((x,y) -> x + y).orElse(0);
        return speedTotal / speedDataPoints;
    }

    private void sendToFirebase(CarDataDto carDataDto) {
        System.out.println(CarDataHelper.serialize(carDataDto));
        //this.restToFirebase(carDataDto.getVin(), CarDataHelper.serialize(carDataDto));
    }

    private void restToFirebase(String key, String json){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(Application.firebase + "/cars/"+ key +".json", json);
    }
}
