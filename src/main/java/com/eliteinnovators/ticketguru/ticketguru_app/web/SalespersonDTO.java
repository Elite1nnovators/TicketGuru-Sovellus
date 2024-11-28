package com.eliteinnovators.ticketguru.ticketguru_app.web;

public class SalespersonDTO {
    private Long salespersonId;
    private String firstName;
    private String lastName;



    public SalespersonDTO() {
    }
    
    public SalespersonDTO(Long salespersonId, String firstName, String lastName) {
        this.salespersonId = salespersonId;
        this.firstName = firstName;
        this.lastName = lastName;

  
    }
    public Long getSalespersonId() {
        return salespersonId;
    }
    public void setSalespersonId(Long salespersonId) {
        this.salespersonId = salespersonId;
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
    
}
