package com.eliteinnovators.ticketguru.ticketguru_app.web;

public class UserDTO {

    private Long userId;
    private String username;
    private String password;
    private String role;
    private SalespersonDTO salesperson;

    public UserDTO() {}

    public UserDTO(Long userId, String username, String password, String role, SalespersonDTO salesperson) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.salesperson = salesperson;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SalespersonDTO getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(SalespersonDTO salesperson) {
        this.salesperson = salesperson;
    }

    

}
