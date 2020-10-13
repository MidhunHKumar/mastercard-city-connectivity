package com.mastercard.codechallenge;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.mastercard.codechallenge.dataloader.RoadMapBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBoot Application class for the project.
 *  @author midhun
 */


@SpringBootApplication
@Slf4j
public class CityConnectivityApplication {
	
	@Autowired
	RoadMapBuilder roadMapBuilder;

	public static void main(String[] args) {
		SpringApplication.run(CityConnectivityApplication.class, args);
		log.info("SpringBoot Application started");
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void buildRoadMap() {
		log.info("After Start up, create the RoadMap from the input data file.");
	    
	    try {
	    	roadMapBuilder.build();
		} catch (IOException e) {
			log.error("Exception Occurred while Building the RoadMap !!!");
			log.error(e.getMessage(), e);
		}
	}

}
