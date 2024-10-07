package com.eliteinnovators.ticketguru.ticketguru_app.web;

public class SalespersonDTO {
    private Long salespersonId;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    public SalespersonDTO() {
    }
    public SalespersonDTO(Long salespersonId, String firstName, String lastName, boolean isAdmin) {
        this.salespersonId = salespersonId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
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
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}
