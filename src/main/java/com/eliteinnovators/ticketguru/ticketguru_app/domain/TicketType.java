package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;
    
    @OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "ticketType-eventTicketType")
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
