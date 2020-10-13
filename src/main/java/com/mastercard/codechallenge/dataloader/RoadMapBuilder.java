package com.mastercard.codechallenge.dataloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mastercard.codechallenge.models.City;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author midhun
 * 
 * Reads the text input file and builds a road map to be used for further connectivity calculations.
 * The application will fails if input data file is not present, not readable or of invalid data format
 */


@Component
@Slf4j
public class RoadMapBuilder {

    private static Map<String, City> roadMap = new HashMap<>();

//  @Value("${data.file:classpath:cities.txt}")
    private static final String INPUT_DATA_FILE_NAME = "cities.txt";

    public Map<String, City> getRoadMap() {
        return roadMap;
    }

    
    public void build() throws IOException {

        log.info("Reading input data from text file : "+ INPUT_DATA_FILE_NAME);
        Stream<String> lines = null;
		try {
			Path path = Paths.get(RoadMapBuilder.class.getClassLoader()
				      .getResource(INPUT_DATA_FILE_NAME).toURI());
			
			lines = Files.lines(path);
			
			lines.forEach(line -> processEachLine(line));
			lines.close();
			
		} catch (Exception e) {
			log.error("RoadMapBuilder : build() Threw Exception !!!");
			log.error(e.getMessage(), e);
		} finally {
			if (null != lines)
				lines.close();
		  }


        log.info("Map: " + roadMap);
    }

	/**
	 * @param line
	 */
	private static void processEachLine(String line) {
		if (!StringUtils.isEmpty(line)) {
			log.debug(line);

		    String[] splitData = line.split(",");
		    if (splitData.length == 2
		    		|| StringUtils.isEmpty(splitData[0])
		    		|| StringUtils.isEmpty(splitData[1])) {
		    	
		    	String orig = splitData[0].trim().toUpperCase();
		        String dest = splitData[1].trim().toUpperCase();
		        if (!orig.equals(dest)) {
		            City origCity = roadMap.getOrDefault(orig, City.build(orig));
		            City destCity = roadMap.getOrDefault(dest, City.build(dest));

		            origCity.addConnectedCity(destCity);
		            destCity.addConnectedCity(origCity);

		            roadMap.put(origCity.getName(), origCity);
		            roadMap.put(destCity.getName(), destCity);
		        }
		    } else {
		    	log.debug("Read a line with invalid data format .. not processing .. checking next lines");
		    }
		    
		} else {
			log.debug("Read an empty line .. not processing .. checking for more lines");
		}
	}

    public City getCity(String name) {
        return roadMap.get(name);
    }

}
