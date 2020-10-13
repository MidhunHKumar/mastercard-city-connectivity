/**
 * 
 */
package com.mastercard.codechallenge.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author midhun
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class CityConnectivityControllerTest {
	
//	@Autowired
//	private MockMvc mockMvc;
	
	@Autowired
    private TestRestTemplate restTemplate;

	
	@Test
    public void testAreCitiesConnectedValid() {

		
        Map<String, String> params = new HashMap<>();
        params.put("origin", "BOSTON");
        params.put("destination", "NEWARK");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        assertEquals("yes", body);
    }
	
	@Test
    public void testAreCitiesConnectedIndirectValid() {

		
        Map<String, String> params = new HashMap<>();
        params.put("origin", "BOSTON");
        params.put("destination", "PHILADELPHIA");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        assertEquals("yes", body);
    }

    @Test
    public void testAreCitiesConnectedInvalid() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "ALBANY");
        params.put("destination", "PHILADELPHIA");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        assertEquals("no", body);
    }

    @Test
    public void testBadRequestCall() {
        ResponseEntity<String> response = restTemplate.exchange("/connected?origin=&destination=", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
