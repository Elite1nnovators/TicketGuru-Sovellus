package com.eliteinnovators.ticketguru.ticketguru_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
            .requestMatchers("/tickets").hasAnyRole("SALESPERSON", "ADMIN")
            .requestMatchers("/orders").hasAnyRole("SALESPERSON", "ADMIN") // TODO lisää esto että vain ADMIN voi suorittaa DELETE (Service-luokassa)
            .requestMatchers("/events").permitAll() // TODO lisää estot että vain ADMIN voi suorittaa POST/PUT/DELETE (Service-luokassa)
            .anyRequest().permitAll()
            )
        .httpBasic(Customizer.withDefaults());

        http.headers(headers -> headers.frameOptions().sameOrigin());

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

        return userDetailsManager;
    }

//--> Mitkä ovat järkevät käyttäjänimet/roolit ja mitä endpointteja niillä voi käsitellä?



//--> PasswordEncoder + Täytyy soveltaa luokkiin, jossa on salasanat esim. (Customer) => PasswordEncoder.encode(PasswordHash) tms.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
