package ssf.ws14.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact implements Serializable {

    private String id;
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "This field is mandatory.")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters.")
    private String name;

    @NotBlank(message = "This field is mandatory.")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "This field is mandatory.")
    @Size(min = 7, message = "Phone number must contain at least 7 digit.")
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past.")
    @NotNull(message = "This field is mandatory")
    @DateTimeFormat(pattern = "MM-DD-YYYY")
    private LocalDate dateOfBirth;

    @Min(value = 10, message = "Must be above 10 years old")
    @Max(value = 100, message = "Must be below 100 years old")
    private Integer age;

    //Contructors
    //Include empty constructor with generated ID
    public Contact() {
        this.id = this.generateId(8);
    }

    public Contact(String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.id = this.generateId(8);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String id, String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    //Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Method to calculate age
    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculatedAge = 0;
        if ((dateOfBirth != null)) {
            calculatedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.age = calculatedAge;
        this.dateOfBirth = dateOfBirth;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    //Method to generate randomised id for each record
    //Synchronous method, so that the id will be unique, taking in 1 request at a time
    private synchronized String generateId(int numOfChars) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numOfChars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        //Reduce string to number of required characters
        return sb.toString().substring(0, numOfChars);
    }

    //Testing
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + "]";
    }   

}