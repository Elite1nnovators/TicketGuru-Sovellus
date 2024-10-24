package com.eliteinnovators.ticketguru.ticketguru_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


// Kommentoituna sillä jos ottaa toimintaan ennen UserDetailsServiceä -> Ei ole oikeuksia suorittaa API-pyyntöjä :)

/*
@Configuration
public class SecurityConfig {
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()  // Vielä tässä vaiheessa kaikille API-pyynnöille
                .and()
            .httpBasic();                       // Basic autorisointi ja csfr
             .and()
            .csrf().disable();  
        
        return http.build();
    }
--> "Admin & Salesperson" käyttäjätiedot (UserDetailsService)?


    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(
            User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build()
        );

        userDetailsManager.createUser(
            User.withUsername("salesperson")
                .password(passwordEncoder().encode("salesperson"))
                .roles("SALESPERSON")
                .build()
        );

        return userDetailsManager;
    }


//--> Mitkä ovat järkevät käyttäjänimet/roolit ja mitä endpointteja niillä voi käsitellä?



//--> PasswordEncoder + Täytyy soveltaa luokkiin, jossa on salasanat esim. (Customer) => PasswordEncoder.encode(PasswordHash) tms.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

*/