/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.repositories;

import com.travelport.restneohack.model.domain.Traveler;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;

public interface TravelerRepository extends GraphRepository<Traveler>{


	Traveler findByEmailAddress(String emailAddress);
        

}