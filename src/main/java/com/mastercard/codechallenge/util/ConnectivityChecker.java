package com.mastercard.codechallenge.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mastercard.codechallenge.models.City;

import java.util.*;

/**
 * 
 * @author midhun
 * 
 * Core logic to check and find the connectivity between cities
 * 
 * Depth first search logic is applied to traverse the nodes of the data structure and find the connection.
 * 
 */

public class ConnectivityChecker {

    private static final Log LOG = LogFactory.getLog(ConnectivityChecker.class);
    

	private static final String YES = "yes";
	
	private static final String NO = "no";

    private ConnectivityChecker() { }

    /**
     * 
     * @param origin the origin
     * @param destination the destination
     * @return yes if cities are connected and no if a connection cannot be found
     * 
     */
    public static String check(City origin, City destination) {

    	String result = NO;

        if (null != origin 
        		&& null != destination) {
        	LOG.debug("Origin: " + origin.getName() + ", destination: " + destination.getName());
        	if (origin.equals(destination)) {
				/* 
				 * If source and destination are same then the result to be returned in yes 
				 * */
            	result  = YES;
            } else if (origin.getConnectedCities().contains(destination)) {
            	/* 
				 * If a direct match is found result to be returned in yes 
				 * */
            	result  = YES;
            } else {
                
            	Set<City> checkedCity = new HashSet<>();
            	checkedCity.add(origin);
            	
                /*
                 * Adding all the connected cities to be checked
                 */
            	Deque<City> citiesToCheck = new ArrayDeque<>();
                citiesToCheck.addAll(origin.getConnectedCities());
                
                while (!citiesToCheck.isEmpty()) {


                	City city = citiesToCheck.peek();
                	
    				/*
    				 * Check each city in the connected cities list against the destination
    				 * specified until a match is found and exit the search loop
    				 * 
    				 */                
                	
                	if (city.equals(destination)) {
                    	result  = YES;
                    	break;
                    }


    				if (checkedCity.contains(city)) {

    					/*
    					 * This city has been checked, so remove it from the list to be checked against
    					 */
    					
    					citiesToCheck.remove(city);

    				} else {
                    	checkedCity.add(city);

    					/* 
    					 * Adding the new City to the cities to be checked list 
    					 * */
                        citiesToCheck.addAll(city.getConnectedCities());
                        
    					/* 
    					 * Then remove the cities that have already been checked from the list 
    					 * */
                        citiesToCheck.removeAll(checkedCity);
                    }
                }
            }
        }

        return result;
    }
}

