package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import org.springframework.data.repository.CrudRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;

public interface SalespersonRepository extends CrudRepository<Salesperson,Long>{

    Salesperson findBySpUsername(String username);

}
