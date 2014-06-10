/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.springframework.data.neo4j.examples.hellograph.repositories;

import org.springframework.data.neo4j.examples.hellograph.domain.Traveler;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;

public interface TravelerRepository extends GraphRepository<Traveler>{


	Traveler findByEmailAddress(String emailAddress);
        

}