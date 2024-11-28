package com.eliteinnovators.ticketguru.ticketguru_app.service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.User;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.SalespersonMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.UserMapper;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eliteinnovators.ticketguru.ticketguru_app.repository.UserRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.web.SalespersonDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.web.UserDTO;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SalespersonService salespersonService;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails user = new org.springframework.security.core.userdetails.User(username,
        curruser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(curruser.getRole()));

        return user;
    }

    public UserDTO getUserByUsername(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDTO foundUser = UserMapper.INSTANCE.userToUserDTO(user);
        return foundUser;
    }

    public UserDTO createUser(UserDTO userDTO, String password) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);
        String role = userDTO.getRole();
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDTO(savedUser);
    }


    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                   .orElseThrow(() -> new RuntimeException("User not found"));
            
        return UserMapper.INSTANCE.userToUserDTO(user);
    }
    

    public UserDTO patchUser(Long userId, Map<String, Object> updates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    existingUser.setUsername((String) value);
                    break;
                case "roles":
                    String role = existingUser.getRole();
                    existingUser.setRole(role);
                    break;
                case "salesperson":
                    @SuppressWarnings("unchecked") Map<String, Object> salespersonData = (Map<String, Object>) value;
                    Long salespersonId = Long.parseLong(salespersonData.get("salespersonId").toString());
                    SalespersonDTO salespersonDTO = salespersonService.getSalespersonById(salespersonId);

                    if (salespersonDTO != null) {
                        Salesperson salesperson = SalespersonMapper.INSTANCE.salespersonDTOToSalesperson(salespersonDTO);
                        existingUser.setSalesperson(salesperson);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.userToUserDTO(updatedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }
    

}
