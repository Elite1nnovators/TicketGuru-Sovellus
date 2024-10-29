package com.eliteinnovators.ticketguru.ticketguru_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


// Kommentoituna sillä jos ottaa toimintaan ennen UserDetailsServiceä -> Ei ole oikeuksia suorittaa API-pyyntöjä :)

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/tickets").hasAnyAuthority("ROLE_SALESPERSON", "ROLE_ADMIN") 
            .requestMatchers("/orders").hasAnyAuthority("ROLE_SALESPERSON", "ROLE_ADMIN") // TODO lisää esto että vain ADMIN voi suorittaa DELETE (Service-luokassa)
            .requestMatchers("/events").permitAll() // TODO lisää estot että vain ADMIN voi suorittaa POST/PUT/DELETE (Service-luokassa)
            .anyRequest().permitAll()
            )
        .httpBasic(Customizer.withDefaults());

    return http.build();
}



//--> "Admin & Salesperson" käyttäjätiedot (UserDetailsService)?
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

        userDetailsManager.createUser(
            User.withUsername("customer")
                .password(passwordEncoder().encode("customer"))
                .roles("CUSTOMER")
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
