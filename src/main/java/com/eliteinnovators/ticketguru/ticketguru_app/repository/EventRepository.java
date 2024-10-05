package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{

    List<Event> findByEventNameAndCity(String eventName, String eventCity);
    List<Event> findByEventName(String eventName);
    List<Event> findByEventCity(String eventCity);

}
