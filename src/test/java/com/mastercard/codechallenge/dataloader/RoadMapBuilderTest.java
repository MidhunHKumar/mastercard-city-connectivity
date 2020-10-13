/**
 * 
 */
package com.mastercard.codechallenge.dataloader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import com.mastercard.codechallenge.models.City;
import com.mastercard.codechallenge.util.ConnectivityChecker;



/**
 * @author midhun
 *
 */
@SpringBootTest
class RoadMapBuilderTest {
	
	@Autowired
	RoadMapBuilder roadMapBuilder;
	
	@BeforeTestExecution
	public void build() throws Exception {
		
			roadMapBuilder.build();
		
    }

	
	@Test
    public void testBuild() {
		assertFalse(roadMapBuilder.getRoadMap().isEmpty(), "Road map building failed.");
    }
	
	@Test
    public void checkConnectedCities() {
        City city1 = roadMapBuilder.getCity("BOSTON");
        City city2 = roadMapBuilder.getCity("NEW YORK");

        assertNotNull(city1, "Invalid test data. City not found: BOSTON");
        assertNotNull(city2, "Invalid test data. City not found: NEW YORK");

       
       assertEquals("yes", ConnectivityChecker.check(city1, city2));
    }

    @Test
    public void checkIndirectConnection() {
    	 City city1 = roadMapBuilder.getCity("BOSTON");
         City city2 = roadMapBuilder.getCity("PHILADELPHIA");

         assertNotNull(city1, "Invalid test data. City not found: BOSTON");
         assertNotNull(city2, "Invalid test data. City not found: PHILADELPHIA");

        
        assertEquals("yes", ConnectivityChecker.check(city1, city2));
    }
	
	/**
	 * Test method for {@link com.mastercard.codechallenge.dataloader.RoadMapBuilder#getRoadMap()}.
	 */
	
	@Test
    public void testGetRoadMap() {
		assertFalse(roadMapBuilder.getRoadMap().isEmpty(), "GetRoadMap failed.");
    }
	

	/**
	 * Test method for {@link com.mastercard.codechallenge.dataloader.RoadMapBuilder#getCity(java.lang.String)}.
	 */
	@Test
	void testGetCity() {
		
		City city = City.build("BOSTON");
        city.addConnectedCity(City.build("NEW YORK"))
                .addConnectedCity(City.build("NEWARK"));
        
		assertEquals(city, roadMapBuilder.getCity("BOSTON"));
	}

}
