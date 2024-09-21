package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCUsername(String username);

}
