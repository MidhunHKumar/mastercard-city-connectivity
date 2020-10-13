package com.mastercard.codechallenge.api;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.codechallenge.dataloader.RoadMapBuilder;
import com.mastercard.codechallenge.models.City;
import com.mastercard.codechallenge.util.ConnectivityChecker;

import lombok.extern.slf4j.Slf4j;


/**
 * REST controller with basic exception handling
 */

@RestController
@Slf4j
public class CityConnectivityController {

    
    private static final String VALID_INPUT_URL = "http://localhost:8080/connected?origin=city1&destination=city2";
    
    private static final String MISSING_PARAMETRS ="Invalid URL pattern - Missing origin or destination city inputs -- Please check and correct as per sample : ";
    
    private static final String INVALID_URL ="Invalid URL pattern -- Please check and correct as per sample : ";

    @Autowired
    RoadMapBuilder roadMapBuilder;

    
    @GetMapping(value = "/connected")
    public String areCitiesConnected(
            @RequestParam String origin,
            @RequestParam String destination) {
    	log.info("Inside areCitiesConnected : origin = [" + origin + "] destination = ["+destination+ "]");

    	if (StringUtils.isNotBlank(origin)
    			&& StringUtils.isNotBlank(destination)) {
    		
    		City originCity = roadMapBuilder.getCity(origin.toUpperCase());
            City destCity = roadMapBuilder.getCity(destination.toUpperCase());
            
            return ConnectivityChecker.check(originCity, destCity);
            
    	} else {
    		log.error("Missing Orinin/Destination in the request -- Throwing IllegalArgumentException");
    		throw new IllegalArgumentException();
    	}
        
    }

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleError() {
		return MISSING_PARAMETRS + VALID_INPUT_URL;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleErrorNew() {
		return INVALID_URL + VALID_INPUT_URL;
	}

}
