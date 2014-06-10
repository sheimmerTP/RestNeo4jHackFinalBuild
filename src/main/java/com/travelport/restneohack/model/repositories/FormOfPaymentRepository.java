/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.ResultColumn;
import com.travelport.restneohack.model.domain.FormOfPayment;
import com.travelport.restneohack.model.domain.Traveler;
import org.springframework.data.neo4j.repository.CypherDslRepository;
import org.springframework.data.neo4j.repository.GraphRepository;


//Note: this repository query's are not compatible with build at this time 6/10/2014
public interface FormOfPaymentRepository extends GraphRepository<FormOfPayment>, CypherDslRepository<FormOfPayment> {

	Page<FormOfPayment> findByDescriptionLike(String description, Pageable pageable);

	List<FormOfPayment> findByAttributesContains(String attribute);

    // search string must be description:text
    @Query("START formOfPayment=node:formOfPayment_search({0}) " +
           " MATCH formOfPayment-[r:RATED]-traveler " +
           " RETURN formOfPayment " +
           " ORDER BY avg(r.stars) DESC")
    Page<FormOfPayment> listFormOfPaymentsRanked(String description, Pageable page);
    
    //Query has not been constructed to work with current project
    @Query("start cat=node:Category(name={0}) match cat-[SUB_CAT*0..5]-leaf<-[:CATEGORY]-formOfPayment return formOfPayment")
    Iterable<FormOfPayment> findByCategory(String category);
    
    //Query has not been constructed to work with current project
    @Query("START cust=node({0}) " +
           " MATCH cust-[r1:RATED]->formOfPayment<-[r2:RATED]-similar-[:ORDERED]->accountView-[:FOPS]->suggestion " +
           " where abs(r1.stars - r2.stars) <= 2 "+
           " RETURN suggestion, count(*) as score" +
           " ORDER BY score DESC")
    List<Suggestion> recommendFormOfPayments(Traveler traveler);
    
    @MapResult
    interface Suggestion {
        @ResultColumn("suggestion") FormOfPayment getFormOfPayment();
        @ResultColumn("score") Integer getScore();
    }
}
