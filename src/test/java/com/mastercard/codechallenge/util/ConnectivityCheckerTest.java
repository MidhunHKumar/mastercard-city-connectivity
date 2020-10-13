/**
 * 
 */
package com.mastercard.codechallenge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mastercard.codechallenge.models.City;

/**
 * @author midhun
 *
 */
class ConnectivityCheckerTest {
	
	/**
	 * Test method for {@link com.mastercard.codechallenge.util.ConnectivityChecker#check(com.mastercard.codechallenge.models.City, com.mastercard.codechallenge.models.City)}.
	 */
	@Test
	void testCheckDirectConnection() {
		
		City city1 = City.build("BOSTON");
        city1.addConnectedCity(City.build("NEW YORK"))
                .addConnectedCity(City.build("NEWARK"));
        
        City city2 = City.build("NEW YORK");
        city2.addConnectedCity(City.build("BOSTON"));
        
        assertEquals("yes", ConnectivityChecker.check(city1, city2));
	}
	
	
	/**
	 * Test method for {@link com.mastercard.codechallenge.util.ConnectivityChecker#check(com.mastercard.codechallenge.models.City, com.mastercard.codechallenge.models.City)}.
	 */
	@Test
	void testCheckNoConnection() {
		City city1 = City.build("ALBANY");
    	city1.addConnectedCity(City.build("TRENTON"));
    	
    	City city2 = City.build("PHILADELPHIA");
    	city2.addConnectedCity(City.build("NEWARK"));
        
    	assertEquals("no", ConnectivityChecker.check(city1, city2));
	}
	
	/**
	 * Test method for {@link com.mastercard.codechallenge.util.ConnectivityChecker#check(com.mastercard.codechallenge.models.City, com.mastercard.codechallenge.models.City)}.
	 */
	@Test
	void testCheckSameCity() {
		City city1 = City.build("ALBANY");
    	city1.addConnectedCity(City.build("TRENTON"));
        
    	assertEquals("yes", ConnectivityChecker.check(city1, city1));
	}

}
