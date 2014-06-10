/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Tag {
    
    @GraphId
    private Long id;
    
    @Indexed(unique = true)
    String name;
    @GraphProperty
    Object value;

    public Tag(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Tag() {
    }
    
     public Long getId() {
            return id;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
