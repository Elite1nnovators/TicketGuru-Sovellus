package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import java.util.*;

import org.mapstruct.*;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.*;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "salesperson.salespersonId", target = "salespersonId")
    @Mapping(source = "salesperson.firstName", target = "salespersonFirstName")
    @Mapping(source = "salesperson.lastName", target = "salespersonLastName")
    OrderDTO toOrderDTO(Order order);
    /*
     * qualifiedByName => apumetodi oikeaan mappaykseen, ks. alempana "default Salesperson idToSalesperson"-metodi esimerkiksi
     */
    @Mapping(target = "salesperson", source = "salespersonId", qualifiedByName = "idToSalesperson")
    Order toOrder(OrderDTO orderDTO, @Context SalespersonRepository salespersonRepository, @Context EventTicketTypeRepository eventTicketTypeRepository);


    @Mapping(source = "eventTicketType.id", target = "eventTicketTypeId")
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);

    @Mapping(target = "eventTicketType", source = "eventTicketTypeId", qualifiedByName = "idToEventTicketType")
    OrderDetails toOrderDetails(OrderDetailsDTO orderDetailsDTO, @Context EventTicketTypeRepository eventTicketTypeRepository);

    List<OrderDTO> toOrderDTOs(List<Order> orders);
    List<OrderDetailsDTO> toOrderDetailsDTOs(List<OrderDetails> orderDetails);
    List<OrderDetails> toOrderDetailsList(List<OrderDetailsDTO> orderDetailsDTO, @Context EventTicketTypeRepository eventTicketTypeRepository);

    /*
     * Metodit mappaamaan Salesperson tai EventTicketType olioksi Id:n perusteella
     * toOrder ja toOrderDetails tarvitsee näitä, jotta Id:t mappaantyy oikein olioiksi
     */

    @Named("idToSalesperson")
    default Salesperson idToSalesperson(Long salespersonId, @Context SalespersonRepository salespersonRepository) {
        if(salespersonId == null) {
            return null;
        }
        return salespersonRepository.findById(salespersonId)
            .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with ID " + salespersonId + " not found"));
    }

    @Named("idToEventTicketType")
    default EventTicketType idToEventTicketType(Long eventTicketTypeId, @Context EventTicketTypeRepository eventTicketTypeRepository) {
        if(eventTicketTypeId == null) {
            return null;
        }
        return eventTicketTypeRepository.findById(eventTicketTypeId)
            .orElseThrow(() -> new EventTicketTypeNotFoundException("EventTicketType with ID " + eventTicketTypeId + " not found"));
    }
}
