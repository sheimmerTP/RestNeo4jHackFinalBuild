/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.util.Assert;

@NodeEntity
public class AccountView {
    
    @GraphId
    private Long id;

    @RelatedTo
    private Traveler traveler;
    @RelatedTo
    private Address billingAddress;
    @RelatedTo
    private Address shippingAddress;

    @RelatedToVia(type = "FOPS")
    @Fetch
    private Set<PaymentType> lineItems = new HashSet<PaymentType>();

    public AccountView(Traveler traveler) {
    	this.traveler = traveler;
    }

    protected AccountView() {

    }

    public void add(PaymentType lineItem) {
        this.lineItems.add(lineItem);
    }
    // TODO JIRA setter was used when hydrating object from storage, don't use BeanWrapper
    public AccountView withBillingAddress(Address billingAddress) {
        Assert.state(traveler.hasAddress(billingAddress),"valid traveler address for "+traveler);
        this.billingAddress = billingAddress;
        return this;
    }

    public AccountView withShippingAddress(Address shippingAddress) {
        Assert.state(traveler.hasAddress(shippingAddress),"valid traveler address for "+traveler);
        this.shippingAddress = shippingAddress;
        return this;
    }
    
        public Long getId() {
            return id;
        }

        public Traveler getTraveler() {
            return traveler;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public Set<PaymentType> getLineItems() {
		return Collections.unmodifiableSet(lineItems);
	}

    public void add(FormOfPayment formOfPayment, int amount) {
        add(new PaymentType(this,formOfPayment,amount));
    }
}