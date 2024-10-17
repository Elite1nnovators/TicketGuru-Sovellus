package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDetailsDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "salesperson.salespersonId", target = "salespersonId")
    @Mapping(source = "customer.firstName", target = "customerFirstName")
    @Mapping(source = "customer.lastName", target = "customerLastName")
    @Mapping(source = "salesperson.firstName", target = "salespersonFirstName")
    @Mapping(source = "salesperson.lastName", target = "salespersonLastName")
    OrderDTO toOrderDTO(Order order);

    @Mapping(target = "customer.customerId", source = "customerId")
    @Mapping(target = "salesperson.salespersonId", source = "salespersonId")
    @Mapping(target = "customer.firstName", source = "customerFirstName")
    @Mapping(target = "customer.lastName", source = "customerLastName")
    @Mapping(target = "salesperson.firstName", source = "salespersonFirstName")
    @Mapping(target = "salesperson.lastName", source = "salespersonLastName")
    Order toOrder(OrderDTO orderDTO);

    @Mapping(source = "ticket.id", target = "ticketId")
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);

    @Mapping(target = "ticket.id", source = "ticketId")
    OrderDetails toOrderDetails(OrderDetailsDTO orderDetailsDTO);

    List<OrderDTO> toOrderDTOs(List<Order> orders);
    List<OrderDetailsDTO> toOrderDetailsDTOs(List<OrderDetails> orderDetails);
    List<OrderDetails> toOrderDetailsList(List<OrderDetailsDTO> orderDetailsDTO);
}
