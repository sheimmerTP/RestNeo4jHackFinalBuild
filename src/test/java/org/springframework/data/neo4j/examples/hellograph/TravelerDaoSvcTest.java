package org.springframework.data.neo4j.examples.hellograph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.examples.hellograph.domain.Address;
import org.springframework.data.neo4j.examples.hellograph.domain.Country;
import org.springframework.data.neo4j.examples.hellograph.domain.EmailAddress;
import org.springframework.data.neo4j.examples.hellograph.domain.Traveler;
import org.springframework.data.neo4j.examples.hellograph.repositories.TravelerRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath:/spring/helloWorldContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true) //turn this to true to rollback any changes and unpopulate the db
@Transactional
public class TravelerDaoSvcTest {

	@Autowired
	private TravelerRepository travelerRepository;
	
	@Autowired
	private Neo4jTemplate template;
        
        @Autowired
        TravelerDaoSvcImpl dataImpl;
	
	@Rollback(false)
	@BeforeTransaction
	public void cleanUpGraph() {
		Neo4jHelper.cleanDb(template);
	}
	
	@Test
        public void savesTravelerCorrectly(){
   //       TravelerDaoSpringDataImpl impl = new TravelerDaoSpringDataImpl();
            EmailAddress email = new EmailAddress("HanSolo@travelport.com");
            Traveler hanSolo = new Traveler("Han", "Solo", email.getEmail());
        
            Country usa=new Country("US","United States");
            hanSolo.add(new Address("27 Broadway", "New York", usa));
            //Traveler result = repository.save(hanSolo);
            Traveler result = dataImpl.createTraveler(hanSolo);
            result.toString();
        
        
//          assertThat(result.getId(), is(notNullValue()));
            assertNotNull(result.getId());
        
        
            Traveler hanny = dataImpl.findByEmailAddress(email.getEmail());
            System.out.println("Traveler email @ dataImpl = " + hanny.getEmailAddress());
            Traveler travelerByAddress = dataImpl.findByEmailAddress(email.getEmail());
            System.out.println("Traveler email @ dataImpl = " + hanSolo.getEmailAddress());
            System.out.println("Traveler = " + hanSolo.toString());
      //    assertThat(result2, is(hanSolo));
    }
        
        @Test
        public void shouldHaveCorrectNumberOfTravelers() {
            dataImpl.makeSomeTravelers();
            assertEquals(4, dataImpl.getNumberOfTravelers());
        }

        @Test
        public void shouldFindWorldsById() {
            dataImpl.makeSomeTravelers();
            for(Traveler traveler : dataImpl.getAllTravelers()) {
        	Traveler foundTraveler = dataImpl.findTravelerById(traveler.getId());
                assertNotNull(foundTraveler);
        }
    }
    
        @Test
        public void shouldFindTravelerbyEmailAddress() {
            dataImpl.makeSomeTravelers();
    	
            for(Traveler traveler : dataImpl.getAllTravelers()) {
        	Traveler foundTraveler = dataImpl.findByEmailAddress(traveler.getEmailAddress());
                Set<Address> addresses = foundTraveler.getAddresses();
                System.out.println("Size of set = " + addresses.size());
                List<Address> addressList = new ArrayList<Address>(addresses);
                for(int i=0; i< addressList.size(); i++){
                    System.out.println(addressList.get(i).getStreet() + " " + addressList.get(i).getCity());
                }
                
                assertNotNull(foundTraveler);
        }
    }
	
}
