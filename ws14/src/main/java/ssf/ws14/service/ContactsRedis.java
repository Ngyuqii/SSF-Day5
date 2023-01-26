package ssf.ws14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ssf.ws14.model.Contact;

@Service //Can be @Repository as well, since this code holds business logic and data storing
public class ContactsRedis {

    //Inject the RedisTemplate into ContactsRedist
    @Autowired
    RedisTemplate<String, Object> rTemplate;

    private static final String CONTACT_HEADER = "Contactlist";

    //Method to save contact into Redis
    //1. List - Adding contact id value into Contactlist
    //2. Map - Adding id-contact object key-value pair into Contactlist_Map
    public void saveContact(final Contact contact){
        rTemplate.opsForList().leftPush(CONTACT_HEADER, contact.getId());
        rTemplate.opsForHash().put( CONTACT_HEADER + "_Map", contact.getId(), contact);
        System.out.println("Saving contact: " + contact.getId()); //checkpoint
    }

    //Method to retrieve contact by contact id
    //Read value contact object from map
    public Contact findContactById(final String contactId){
        Contact result= (Contact)rTemplate.opsForHash().get(CONTACT_HEADER+ "_Map", contactId);
        return result;
    }

    //Method to retrieve all contacts starting from startIndex
    //1. Get a list of all elements id between startIndex and 15
    //2. Get all contact object from map corresponding to id in the above list
    public List<Contact> findMultiContacts(int startIndex){
        List<Object> contactListID = rTemplate.opsForList().range(CONTACT_HEADER, startIndex, 15);
        List<Contact> ctcs = rTemplate.opsForHash()
            .multiGet(CONTACT_HEADER+ "_Map", contactListID)
            .stream()
            .filter(Contact.class::isInstance) //Ensure object is in Contact class
            .map(Contact.class::cast)
            .toList();

        return ctcs;
    }
    
}