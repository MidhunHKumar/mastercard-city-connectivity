/**
 * 
 */
package com.mastercard.codechallenge.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;


/**
 * @author midhun
 *
 */
class CityTest {

	
	@Test
    public void testBuild() {
        City city = City.build("BOSTON");
        assertEquals("BOSTON", city.getName());
    }

    @Test
    public void buildWithConnections() {
        City city = City.build("BOSTON");
        city.addConnectedCity(City.build("NEW YORK"))
                .addConnectedCity(City.build("NEWARK"));

        Set<City> cities = city.getConnectedCities();
        assertEquals(2, cities.size());
        assertTrue(cities.contains(City.build("NEWARK")));
    }


    @Test
    public void addConnection() {
        City city = City.build("BOSTON");
        city.addConnectedCity(City.build("NEW YORK"))
        	.addConnectedCity(City.build("NEWARK"));

        assertEquals(2, city.getConnectedCities().size());
    }

    @Test
    public void addDuplicatesConnections() {
        City city = City.build("BOSTON");
        city.addConnectedCity(City.build("NEWARK"))
    		.addConnectedCity(City.build("NEWARK"))
    			.addConnectedCity(City.build("NEWARK"));

        assertEquals(1, city.getConnectedCities().size());
    }

}
