package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import java.util.*;

import org.mapstruct.*;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.*;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "salesperson.salespersonId", target = "salespersonId")
    @Mapping(source = "customer.firstName", target = "customerFirstName")
    @Mapping(source = "customer.lastName", target = "customerLastName")
    @Mapping(source = "salesperson.firstName", target = "salespersonFirstName")
    @Mapping(source = "salesperson.lastName", target = "salespersonLastName")
    OrderDTO toOrderDTO(Order order);

    @Mapping(target = "customer", source = "customerId")
    @Mapping(target = "salesperson", source = "salespersonId")
    Order toOrder(OrderDTO orderDTO, @Context CustomerRepository customerRepository, @Context SalespersonRepository salespersonRepository);

    @Mapping(source = "ticket.id", target = "ticketId")
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);

    @Mapping(target = "ticket.id", source = "ticketId")
    OrderDetails toOrderDetails(OrderDetailsDTO orderDetailsDTO, @Context TicketRepository ticketRepository);

    List<OrderDTO> toOrderDTOs(List<Order> orders);
    List<OrderDetailsDTO> toOrderDetailsDTOs(List<OrderDetails> orderDetails);
    List<OrderDetails> toOrderDetailsList(List<OrderDetailsDTO> orderDetailsDTO, @Context TicketRepository ticketRepository);

    // TODO mapCustomer CustomerMapperiin kun valmis
    default Customer mapCustomer(Long customerId, @Context CustomerRepository customerRepository) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));
    }

    // TODO mapSalesperson SalespersonMapperiin kun valmis
    default Salesperson mapSalesperson(Long salespersonId, @Context SalespersonRepository salespersonRepository) {
        return salespersonRepository.findById(salespersonId)
            .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with ID " + salespersonId + " not found"));
    }

    // TODO mapTicket TicketMapperiin kun valmis
    default Ticket mapTicket(Long ticketId, @Context TicketRepository ticketRepository) {
        return ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket with ID " + ticketId + " not found")); // TODO RTE -> ticketin omaksi erroriksi kun löytyy
    }
}
