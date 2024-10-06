package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketCode;
    private boolean isValid;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetails> orderDetails = new ArrayList<>(); //TODO: tarvitsee setterin ja getterin, setterissä asetetaan for-loopilla myös kaikkiin orderdetaileihin ticket

    @ManyToOne
    @JoinColumn(name = "eventTicketType_id")
    @JsonBackReference
    private EventTicketType eventTicketType;

    
    public Ticket() {
    }

    public Ticket(EventTicketType eventTicketType, String ticketCode, boolean isValid) {
        this.eventTicketType = eventTicketType;
        this.ticketCode = ticketCode;
        this.isValid = isValid;

    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public EventTicketType getEventTicketType() {
        return eventTicketType;
    }

    public void setEventTicketType(EventTicketType eventTicketType) {
        this.eventTicketType = eventTicketType;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", ticketCode=" + ticketCode + ", isValid=" + isValid + ", orderDetails="
                + orderDetails + ", eventTicketType=" + eventTicketType + "]";
    }

    

}
