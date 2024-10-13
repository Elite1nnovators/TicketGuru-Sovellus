package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import org.mapstruct.Mapper;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
// import com.eliteinnovators.ticketguru.ticketguru_app.web.EventDTO; -- Lisää tämä, kunhan EventDTO on luotu

@Mapper (componentModel = "spring")
public class EventMapper {
/* 
    @Mapping(source = "event.eventId", target = "eventId")
    @Mapping(source = "eventName", target = "eventName")
    @Mapping(source = "eventDate", target = "eventDate")
    @Mapping(source = "eventCity", target = "eventCity")
    @Mapping(source = "eventTicketTypes", target = "eventTicketTypes")
    EventDTO toEventDTO(Event event);
    

    @Mapping(target = "event.eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "eventCity", source = "eventCity")
    @Mapping(target = "eventTicketTypes", source = "eventTicketTypes")
    Event toEvent(EventDTO eventDTO);

    Mapperilla määritellään, mitä tietoa halutaan siirtää Event luokasta EventDTO,
    sekä päinvastoin.
    Mapperi ottaa objektin (tässä tapauksessa Event) ja muuntaa sen toiseen muotoon (EventDTO). 
    Samalla se voi myös muuntaa takaisin EventDTO Event-objektiin.
    Tämä on hyödyllistä, kun halutaan jakaa vain osa tiedoista (esim REST kutsuissa).
    */

}
