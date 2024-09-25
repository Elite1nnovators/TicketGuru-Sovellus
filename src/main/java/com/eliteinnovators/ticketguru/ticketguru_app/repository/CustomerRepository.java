package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import org.springframework.data.repository.CrudRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerUsername(String username);

}
