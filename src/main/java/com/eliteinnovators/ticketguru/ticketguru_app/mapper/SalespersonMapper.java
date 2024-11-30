package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.web.SalespersonDTO;

@Mapper(componentModel = "spring")
public interface SalespersonMapper {

    SalespersonMapper INSTANCE = Mappers.getMapper(SalespersonMapper.class);

    
    SalespersonDTO salespersonToSalespersonDTO(Salesperson salesperson);

    Salesperson salespersonDTOToSalesperson(SalespersonDTO salespersonDTO);

}
