package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        try {
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

           
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.print("_____AUTHENTICATED USER " + authentication.getName());
            return authentication.getName();
        } catch (Exception ex) {
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Invalid username or password";
        }
    }

}
