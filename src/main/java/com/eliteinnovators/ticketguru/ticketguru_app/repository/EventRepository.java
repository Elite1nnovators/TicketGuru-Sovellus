package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventNameAndEventCity(String eventName, String eventCity);

    List<Event> findAllByEventName(String eventName);
    Optional<Event> findByEventName(String eventName);

    List<Event> findByEventCity(String eventCity);

}
