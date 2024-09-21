package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import org.springframework.data.repository.CrudRepository;

public interface SalespersonRepository extends CrudRepository<Salesperson,Long>{

    Salesperson findBySpUsername(String username);

}
