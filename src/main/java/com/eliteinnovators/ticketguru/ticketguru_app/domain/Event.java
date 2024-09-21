package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Event {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;
    private String event_name;
    private String event_date;
    private String event_address;
    private String event_city;
    private String event_description;

    //Tähän lisätään yhteys Ticket-tauluun:
    //@OneToMany(mappedBy = "Event")
    //private List<Ticket> tickets;


    public Event() {
    }

    public Long getEvent_id() {
        return event_id;
    }


    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }


    public String getEvent_name() {
        return event_name;
    }


    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }


    public String getEvent_date() {
        return event_date;
    }


    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }


    public String getEvent_address() {
        return event_address;
    }


    public void setEvent_address(String event_address) {
        this.event_address = event_address;
    }


    public String getEvent_city() {
        return event_city;
    }


    public void setEvent_city(String event_city) {
        this.event_city = event_city;
    }


    public String getEvent_description() {
        return event_description;
    }


    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    @Override
    public String toString() {
        return "Event [event_id=" + event_id + ", event_name=" + event_name + ", event_date=" + event_date
                + ", event_address=" + event_address + ", event_city=" + event_city + ", event_description="
                + event_description + "]";
    }

    
    
}
