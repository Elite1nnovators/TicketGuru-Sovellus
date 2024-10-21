package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.web.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "event.eventId", target = "eventId")
    @Mapping(source = "event.eventName", target = "eventName")
    @Mapping(source = "event.eventDate", target = "eventDate")
    @Mapping(source = "event.eventCity", target = "eventCity")
    @Mapping(source = "event.eventAddress", target = "eventAddress")
    @Mapping(source = "event.eventDescription", target = "eventDescription")
    @Mapping(source = "event.eventTicketTypes", target = "eventTicketTypes") // Assuming EventTicketTypeDTO is properly defined
    EventDTO toEventDTO(Event event);

    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "eventCity", source = "eventCity")
    @Mapping(target = "eventAddress", source = "eventAddress")
    @Mapping(target = "eventDescription", source = "eventDescription")
    @Mapping(target = "eventTicketTypes", source = "eventTicketTypes")
    Event toEvent(EventDTO eventDTO);

    List<EventDTO> toEventDTOs(List<Event> events);
}
