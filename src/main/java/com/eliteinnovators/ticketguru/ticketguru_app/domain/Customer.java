package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @Column(unique = true)
    private String username;

    //Lisää myös salasana

    private String firstName, lastName, phone, email, address, city;

    /* Muista lisätä tälle myös getterit ja setterit ja generoi constructorit
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    */

    public Customer() {
    }

    public Customer(Long customer_id, String username, String firstName, String lastName, String phone, String email,
            String address, String city) {
        this.customer_id = customer_id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer [customer_id=" + customer_id + ", username=" + username + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", address=" + address
                + ", city=" + city + "]";
    }



    

}
