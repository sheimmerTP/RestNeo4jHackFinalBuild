/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import org.springframework.util.Assert;

import java.util.regex.Pattern;
import org.springframework.data.neo4j.annotation.GraphId;

public class EmailAddress {
    
        @GraphId
        private Long id;

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

	private String email;

	public EmailAddress(String emailAddress) {
		Assert.isTrue(isValid(emailAddress), "Invalid email address!");
		this.email = emailAddress;
	}

	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}
        
        public Long getId() {
    	return id;
        }

	@Override
	public String toString() {
		return email;
	}

    public String getEmail() {
        return email;
    }
}
