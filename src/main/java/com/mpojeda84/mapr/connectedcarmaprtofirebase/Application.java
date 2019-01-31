package com.mpojeda84.mapr.connectedcarmaprtofirebase;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@SpringBootApplication
@EnableScheduling
public class Application {

	private static String table;
	private static String firebase;

	public static void main(String[] args) throws ParseException {

		CommandLine commandLine = new DefaultParser().parse(Configuration.generateOptions(), args);
		table = commandLine.getOptionValue("t");
		firebase = commandLine.getOptionValue("f");

		SpringApplication.run(Application.class, args);

	}

	@Scheduled(cron = "0 * * * * *")
	void sendToFirebase(){
		System.out.println("here");

		Map<String, String> all = MapRDBDataAcess.loadAllFromMapRDB(table);
		all.forEach(this::restToFirebase);
	}

	private void restToFirebase(String key, String json){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(firebase + "/cars/"+ key +".json", json);
	}

}

