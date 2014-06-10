/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.springframework.util.Assert;

@RelationshipEntity(type = "FOPS")
public class PaymentType {

    @GraphId
    private Long id;
    
    @StartNode
    private AccountView accountView;
    
    @Fetch
    @EndNode
    private FormOfPayment formOfPayment;

    private int amount;

    public PaymentType(AccountView accountView, FormOfPayment formOfPayment) {
    	this(accountView,formOfPayment, 1);
    }

    public PaymentType(AccountView accountView, FormOfPayment formOfPayment, int amount) {
    Assert.notNull(formOfPayment);
    Assert.notNull(accountView);

    this.accountView = accountView;
	this.formOfPayment = formOfPayment;
	this.amount = amount;
    }

    public PaymentType() {

    }
    
    public Long getId() {
    	return id;
    }

    public FormOfPayment getFormOfPayment() {
	return formOfPayment;
    }

    public AccountView getOrder() {
        return accountView;
    }

    public int getAmount() {
	return amount;
    }
}
