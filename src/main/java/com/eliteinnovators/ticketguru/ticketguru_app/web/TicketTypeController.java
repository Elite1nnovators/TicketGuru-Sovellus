package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/tickettypes")
public class TicketTypeController {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketType>> getAllTicketTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketTypeRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TicketType> getTicketTypeById(@PathVariable Long id) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(id);
        if (ticketType.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ticketType.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType newTicketType) {
        // Tarkistetaan onko samalla nimellä jo olemassa lipputyyppi
        TicketType existingTicketType = ticketTypeRepository.findByName(newTicketType.getName()).orElse(null);
        if (existingTicketType!= null) {
            return ResponseEntity.ok(existingTicketType);
        }

        // Jos ei, luodaan uusi lipputyyppi
        TicketType savedTicketType = ticketTypeRepository.save(newTicketType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicketType);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TicketType> updateTicketType(@PathVariable Long id, @RequestBody TicketType ticketType) {
        // Tarkistetaan, että lipputyyppi on olemassa ennen päivittämistä
        if (!ticketTypeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Palautetaan 404, jos lipputyyppiä ei löydy
        }

        // Päivitetään lipputyyppi
        ticketType.setId(id); // Varmistetaan, että käytetään oikeaa ID:tä
        TicketType updatedTicketType = ticketTypeRepository.save(ticketType);

        // Palautetaan päivitetty lipputyyppi ja status 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(updatedTicketType);
    }

    

    
    
    



}
