package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>{

}
