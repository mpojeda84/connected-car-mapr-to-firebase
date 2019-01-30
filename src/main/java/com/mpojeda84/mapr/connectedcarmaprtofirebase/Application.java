package com.mpojeda84.mapr.connectedcarmaprtofirebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Scheduled(cron = "0 * * * * *")
	void sendToFirebase(){
		System.out.println("here");

		List<CarDataDto> all = MapRDBDataAcess.loadAllFromMapRDB();
		all.forEach(this::restToFirebase);
	}

	private void restToFirebase(CarDataDto carDataDto){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("https://connected-car-3f879.firebaseio.com/cars/"+ carDataDto.getVin() +".json", carDataDto);
	}

}

