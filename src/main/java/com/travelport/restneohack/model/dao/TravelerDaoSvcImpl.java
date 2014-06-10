/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.dao;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.neo4j.graphdb.DynamicRelationshipType.withName;
import org.neo4j.graphdb.GraphDatabaseService;

import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.core.GraphDatabase;
import com.travelport.restneohack.model.domain.AccountView;
import com.travelport.restneohack.model.domain.Address;
import com.travelport.restneohack.model.domain.Country;
import com.travelport.restneohack.model.domain.EmailAddress;
import com.travelport.restneohack.model.domain.FormOfPayment;
import com.travelport.restneohack.model.domain.Traveler;
import com.travelport.restneohack.model.domain.World;
import com.travelport.restneohack.model.repositories.FormOfPaymentRepository;
import com.travelport.restneohack.model.repositories.TravelerRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sheimmer
 */


@Service
@Transactional
public class TravelerDaoSvcImpl {    
    
    @Autowired
    private TravelerRepository travelerRepository;
    
    @Autowired
    private FormOfPaymentRepository formOfPaymentRepository;
    
    @Autowired
    private Neo4jTemplate template;
    
 //   @Autowired
 //   GraphDatabase graphDatabase;
    
/*    public Traveler createTraveler(String firstName, String lastName, String emailAddress){
        
    Traveler traveler = new Traveler(firstName, lastName, emailAddress);    
    
    Transaction tx = graphDatabase.beginTx();
    
    try{
        travelerRepository.save(traveler);
        
        //what relationships are we assigning here?
        //assign address to traveler, but address has to be created and passed in as well
        
        tx.success();

        }finally {
            tx.finish();
        }
        
        //quicker way return TravelerRepository.save(new Traveler(firstName, lastName, emailAddress));
}*/ 
    public Traveler addAddress(Address address, Traveler traveler){
        
        String travEMail= traveler.getEmailAddress();
 //       traveler = findByEmailAddress(travEMail);
        traveler.add(address);
        return travelerRepository.save(traveler);
        
    }
                
    public Traveler createTraveler(Traveler traveler){
        //return travelerRepository.save(new Traveler(firstName, lastName, emailAddress));
        System.out.println("Traveler = " + traveler.toString());
        String firstName=traveler.getFirstName();
        String lastName = traveler.getLastName();
        String emailAddress = traveler.getEmailAddress();
    //    return worldRepository.save(new World(name, moons));
        return travelerRepository.save(new Traveler(firstName, lastName, emailAddress));
                //return travelerRepository.save(traveler);

    }
    
    public Traveler findOne(Long id){
        return travelerRepository.findOne(id);
    }
    
    public Traveler findByEmailAddress(String emailAddress){
        return travelerRepository.findByEmailAddress(emailAddress);
    }
    
    public Iterable<Traveler> getAllTravelers() {
        return travelerRepository.findAll();
    }
    
    public Traveler findTravelerById(Long id) {
		return travelerRepository.findOne(id);
	}
    
    public long getNumberOfTravelers() {
		return travelerRepository.count();
	}
    
    public Collection<Traveler> makeSomeTravelers(){
        Collection<Traveler> travelers = new ArrayList<>();
        Country usa = new Country ("US", "United Stats of America");
        Country China = new Country ("CH", "China");
        
        EmailAddress lukeEmail = new EmailAddress ("lskywalker@alliance.com");
        Traveler travelerLuke = new Traveler ("Luke", "Skywalker", lukeEmail.getEmail());
        Address lukeAddress = new Address ("125 Broadyway", "New York", usa);
        travelerLuke.add(lukeAddress);
        System.out.println("Traveler @ getAddresses() = " + travelerLuke.getAddresses().size());
      //  travelerRepository.save(travelerLuke);
        
        EmailAddress vaderEmail = new EmailAddress ("darthVader@empire.com");
        Traveler travelerVader = new Traveler ("Darth", "Vader", vaderEmail.getEmail());
        Address vaderAddress = new Address ("555 Death Star ln", "New York", usa);
        travelerVader.add(vaderAddress);
        
        EmailAddress chewyEmail = new EmailAddress ("chewbacca@alliance.com");
        Traveler travelerChewy = new Traveler ("Chew", "bacca ", chewyEmail.getEmail());
        Address chewyAddress = new Address ("6623 Delta Dr", "Texas", usa);
        travelerChewy.add(chewyAddress);
        
        EmailAddress obiWanEmail = new EmailAddress ("obiKenobi@JediAcademy.com");
        Traveler travelerObiWan = new Traveler ("Obi-Wan ", "Kenobi", obiWanEmail.getEmail());
        Address obiWanAddress = new Address ("7895 Yang Ching ln", "Hong Kong", China);
        travelerObiWan.add(obiWanAddress);
        
        //Create travelers in graphdb and add travelers to list
        travelers.add(createTraveler(travelerLuke));
        travelers.add(createTraveler(travelerVader));
        travelers.add(createTraveler(travelerChewy));
        travelers.add(createTraveler(travelerObiWan));

        
        return travelers;
    }
    
    public void persistTravelertoDd(Traveler traveler, Set<Address> addresses, FormOfPayment FOP) {
        
        
        Traveler persistTraveler = template.save(traveler);
        
        
        for (Address travelerAddress: addresses){
            Country country = travelerAddress.getCountry();
            template.save(country);
            template.save(travelerAddress);
            System.out.println("Address to be added to persistTraveler = " + 
                    travelerAddress.getStreet() + " " + travelerAddress.getCity());
            persistTraveler.add(travelerAddress);
        }
        
        template.save(FOP);
        
        AccountView accountView = new AccountView(persistTraveler);
        accountView.add(FOP,2);
        template.save(accountView);
        
       
//        template.save(address);
//        template.save(country);
//        address.
//        template.save(new Traveler("Carter","Beauford","carter@dmband.com"));
//        template.save(new Traveler("Boyd","Tinsley","boyd@dmband.com"));
//        final Country usa = template.save(new Country("US", "United States"));
//        template.save(new Address("27 Broadway","New York",usa));
//        iPad = template.save(new FormOfPayment("iPad", "Apple tablet device").withPrice(BigDecimal.valueOf(499D)));
//        mbp = template.save(new FormOfPayment("MacBook Pro", "Apple notebook").withPrice(BigDecimal.valueOf(1299D)));
//        final AccountView accountView = new AccountView(dave);
//        accountView.add(FOP,2);
//        template.save(accountView);
//        
//        template.s
//                
//                
//                 dave = template.save(new Traveler("Dave", "Matthews", "dave@dmband.com"));
//        template.save(new Traveler("Carter","Beauford","carter@dmband.com"));
//        template.save(new Traveler("Boyd","Tinsley","boyd@dmband.com"));
//        final Country usa = template.save(new Country("US", "United States"));
//        template.save(new Address("27 Broadway","New York",usa));
//        iPad = template.save(new FormOfPayment("iPad", "Apple tablet device").withPrice(BigDecimal.valueOf(499D)));
//        mbp = template.save(new FormOfPayment("MacBook Pro", "Apple notebook").withPrice(BigDecimal.valueOf(1299D)));
//        final AccountView accountView = new AccountView(dave);
//        accountView.add(iPad,2);
//        accountView.add(mbp,1);
//        template.save(accountView);
    }
}
