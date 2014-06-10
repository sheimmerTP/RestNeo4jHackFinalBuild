/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.fieldaccess.DynamicProperties;
import org.springframework.data.neo4j.fieldaccess.PrefixedDynamicProperties;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class FormOfPayment {
    
    @GraphId
        private Long id;
    @Indexed(unique = true)
	private String name;
    @Indexed(indexType = IndexType.FULLTEXT,indexName = "formOfPayment_search")
	private String description;
    @GraphProperty(propertyType = double.class)
	private BigDecimal price;

    @RelatedTo
	private Set<Tag> tags = new HashSet<Tag> ();

    private DynamicProperties attributes=new PrefixedDynamicProperties("attributes");

	public FormOfPayment(String name) {
		this(name, null);
	}

	public FormOfPayment(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public FormOfPayment() {

	}

	public void setAttribute(String key, String value) {
            this.attributes.setProperty(key, value);
	}
        
        public Long getId() {
            return id;
        }

	public String getFoPName() {
            return name;
	}

	public String getFoPDescription() {
            return description;
	}

	public Set<Tag> getTags() {
            return Collections.unmodifiableSet(tags);
	}

        public DynamicProperties getAttributes() {
            return attributes;
	}

	public BigDecimal getPrice() {
            return price;
	}

        public FormOfPayment withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        @Override
        public String toString() {
            return name;
        }
}
