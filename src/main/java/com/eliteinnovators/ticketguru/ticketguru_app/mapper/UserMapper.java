package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.User;

import com.eliteinnovators.ticketguru.ticketguru_app.web.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "password", source = "passwordHash")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "passwordHash", source = "password")
    User userDTOToUser(UserDTO userDTO);


}
