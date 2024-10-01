package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;
import java.util.ArrayList;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
    private List<EventTicketType> eventTicketTypes = new ArrayList<>(); 

    public TicketType(String name) {
        this.name = name;
    }

    public TicketType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
