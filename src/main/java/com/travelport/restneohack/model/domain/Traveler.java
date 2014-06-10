/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.util.Assert;

@NodeEntity
public class Traveler {
    
        private final static String HAS_ADDRESS = "ADDRESS";

	private String firstName, lastName;
        
        @GraphId
        private Long id;

	@Indexed(unique = true)
	private String emailAddress;
        
        @Fetch
	@RelatedTo(elementClass = Address.class, type = HAS_ADDRESS)//require direction type here to address
	private Set<Address> addresses = new HashSet<Address>();
       // private Set<Address> addresses = new HashSet<Address>();

        public Traveler() {

	}
        
        public Long getId() {
            return id;
        }
	public Traveler(String firstName, String lastName, String emailAddress) {

                Assert.hasText(firstName);
		Assert.hasText(lastName);
		Assert.hasText(emailAddress);

		this.firstName = firstName;
		this.lastName = lastName;
                this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

        public void addAddress(Address address) {
		addresses.add(address);
	}
        
	public Set<Address> getAddresses() {
            return addresses;
	//	return Collections.unmodifiableSet(addresses);
	}

        public boolean hasAddress(Address address) {
            return addresses.contains(address);
        }

    @Override
    public String toString() {
        //return String.format("%s %s <%s>",firstName,lastName,emailAddress);
        for (Address s : addresses){
            System.out.println(s);
        }
        return String.format("%s %s <%s>",firstName,lastName,emailAddress);
    }
}

    

