/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

/**
 *
 * @author sheimmer
 */
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Address {
    
    @GraphId
    private Long id;

    private String street, city;

    private Country country;

	public Address(String street, String city, Country country) {
		this.street = street;
		this.city = city;
                this.country = country;
    }

	public Address() {

	}
        
        public Long getId() {
                return id;
        }

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public Country getCountry() {
		return country;
	}
        
        @Override
        public String toString(){
            return String.format("%s %s <%s>", street, city, country);
        }
    
}
