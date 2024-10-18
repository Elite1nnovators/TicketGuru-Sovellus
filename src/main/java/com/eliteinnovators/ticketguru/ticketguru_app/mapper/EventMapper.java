package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import org.mapstruct.Mapper;

//import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
// import com.eliteinnovators.ticketguru.ticketguru_app.web.EventDTO; -- Lisää tämä, kunhan EventDTO on luotu

@Mapper (componentModel = "spring")
public interface EventMapper {
/* 
    @Mapping(source = "event.eventId", target = "eventId")
    @Mapping(source = "event.eventName", target = "eventName")
    @Mapping(source = "event.eventDate", target = "eventDate")
    @Mapping(source = "event.eventCity", target = "eventCity")
    @Mapping(source = "event.eventTicketTypes", target = "eventTicketTypes")
    EventDTO toEventDTO(Event event);
    

    @Mapping(target = "event.eventId", source = "eventId")
    @Mapping(target = "event.eventName", source = "eventName")
    @Mapping(target = "event.eventDate", source = "eventDate")
    @Mapping(target = "event.eventCity", source = "eventCity")
    @Mapping(target = "event.eventTicketTypes", source = "eventTicketTypes")
    Event toEvent(EventDTO eventDTO);

    List<EventDTO> toEventDTOs(List<Event> events);

    Mapperilla määritellään, mitä tietoa halutaan siirtää Event luokasta EventDTO,
    sekä päinvastoin.
    Mapperi ottaa objektin (tässä tapauksessa Event) ja muuntaa sen toiseen muotoon (EventDTO). 
    Samalla se voi myös muuntaa takaisin EventDTO Event-objektiin.
    Tämä on hyödyllistä, kun halutaan jakaa vain osa tiedoista (esim REST kutsuissa).
    */

}
