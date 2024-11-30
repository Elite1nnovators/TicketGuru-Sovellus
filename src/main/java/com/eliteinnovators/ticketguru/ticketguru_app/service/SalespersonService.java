package com.eliteinnovators.ticketguru.ticketguru_app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.User;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.SalespersonMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.UserMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.UserRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.web.SalespersonDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.web.UserDTO;

@Service
public class SalespersonService {

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired 
    private SalespersonMapper salespersonMapper;

    @Autowired
    private UserRepository userRepository;


    public SalespersonDTO createSalesperson(SalespersonDTO salespersonDTO) {

        Salesperson salesperson = salespersonMapper.salespersonDTOToSalesperson(salespersonDTO);
        Salesperson savedSalesperson = salespersonRepository.save(salesperson);

        return salespersonMapper.salespersonToSalespersonDTO(savedSalesperson);
    }

    public List<SalespersonDTO> getAllSalespersons() {
        List<Salesperson> salespersons = salespersonRepository.findAll();
        return salespersons.stream()
                .map(SalespersonMapper.INSTANCE::salespersonToSalespersonDTO)
                .collect(Collectors.toList());
    }
    
    public SalespersonDTO getSalespersonById(Long salespersonId) {
        return salespersonRepository.findById(salespersonId)
                .map(salespersonMapper::salespersonToSalespersonDTO)
                .orElseThrow(() -> new RuntimeException("Salesperson with ID " + salespersonId + " not found"));
    }

    public SalespersonDTO updateSalesperson(Long salespersonId, SalespersonDTO salespersonDTO) {

        if (salespersonRepository.existsById(salespersonId)) {
            Salesperson salesperson = salespersonMapper.salespersonDTOToSalesperson(salespersonDTO);
            salesperson.setSalespersonId(salespersonId);

            Salesperson updatedSalesperson = salespersonRepository.save(salesperson);
            return salespersonMapper.salespersonToSalespersonDTO(updatedSalesperson);
       
    
        }
        return null;
    }

    public boolean deleteSalesperson(Long salespersonId) {
        
        if (salespersonRepository.existsById(salespersonId)) {
        salespersonRepository.deleteById(salespersonId);
            return true;
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public SalespersonDTO getSalespersonByUsername(String username) {
        
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Salesperson salesperson = user.getSalesperson();
        return salespersonMapper.salespersonToSalespersonDTO(salesperson);
        
    }


}
