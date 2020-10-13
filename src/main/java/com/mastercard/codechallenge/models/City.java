package com.mastercard.codechallenge.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Defines a City along with the connections to other cities
 *  @author midhun
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String name;

    @EqualsAndHashCode.Exclude
    private Set<City> connectedCities = new HashSet<>();

    private City(String name) {
    	if (StringUtils.isNotBlank(name)) {
    		this.name = name.trim().toUpperCase();
    	} else {
    		throw new IllegalArgumentException();
    	}
    }


    public static City build(String name) {
        return new City(name);
    }

    public City addConnectedCity(City city) {
        connectedCities.add(city);
        return this;
    }

    public Set<City> getConnectedCities() {
        return connectedCities;
    }
    
    @Override
    public String toString() {

        return "City{" +
                "name='" + name + "'" +
                ", connectedCities='" + prettyPrint() +
                "'}";
    }

    public String prettyPrint() {
        return connectedCities
                .stream()
                .map(City::getName)
                .collect(Collectors.joining(","));
    }

    

}
