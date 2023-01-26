package ssf.ws14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ssf.ws14.model.Contact;
import ssf.ws14.service.ContactsRedis;

@Controller
public class AddressBookController {

    //Integrate ContactRedis into the controller
    @Autowired
    private ContactsRedis cRedisSvc;

    //Show form for user input
    @GetMapping(path="/")
    public String contactForm(Model model){
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    //Method to save contact details and return page showing input values
    //Perform error validation
    @PostMapping("/contact")
    public String savingContact(@Valid Contact contact, BindingResult result, Model model, HttpServletResponse response){
        if(result.hasErrors()){
            return "contact";
        }
        cRedisSvc.saveContact(contact);
        model.addAttribute( "contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED); //return http status
        return "showContact";
    }

    //Method to retrieve all contacts starting from startIndex and return list of contacts
    @GetMapping("/contact")
    public String getContacts(Model model, @RequestParam(name="startIndex") Integer startIndex){
        List<Contact> result = cRedisSvc.findMultiContacts(startIndex);
        model.addAttribute("contacts", result);
        return "listContact";
    }

    //Method to retrieve contact by contact id ad return requested contact
    @GetMapping(path="/contact/{contactId}")
    public String getContactInfoById(Model model, @PathVariable(value="contactId") String contactId){
        Contact ctc = cRedisSvc.findContactById(contactId);
        ctc.setId(contactId);
        model.addAttribute("contact", ctc);
        return "showContact";
    }
    
}
