package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Salesperson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesperson_id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    private boolean isAdmin;

    private String firstName, lastName, phone;

/* Muista lisätä tälle myös getterit ja setterit ja generoi constructorit
    
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<Order> orders;

    */

    public Salesperson() {}

    public Salesperson(Long salesperson_id, String username, String passwordHash, boolean isAdmin, String firstName, String lastName,
            String phone) {
        this.salesperson_id = salesperson_id;
        this.username = username;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Long getSalesperson_id() {
        return salesperson_id;
    }

    public void setSalesperson_id(Long salesperson_id) {
        this.salesperson_id = salesperson_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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

    @Override
    public String toString() {
        return "Salesperson [salesperson_id=" + salesperson_id + ", username=" + username + ", isAdmin=" + isAdmin
                + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + "]";
    }

    

    

}
