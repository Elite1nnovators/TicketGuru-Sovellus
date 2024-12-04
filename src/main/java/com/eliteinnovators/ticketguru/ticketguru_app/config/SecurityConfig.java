package com.eliteinnovators.ticketguru.ticketguru_app.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;


// Kommentoituna sillä jos ottaa toimintaan ennen UserDetailsServiceä -> Ei ole oikeuksia suorittaa API-pyyntöjä :)

@Configuration
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/auth/login", "/api/auth/logout", "/api/users").permitAll() // Allow REST login/logout
            .requestMatchers("/tickets", "/index", "/ticketdashboard").hasAnyRole("SALESPERSON", "ADMIN")
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic Authentication for APIs
        .logout(logout -> logout
            .logoutUrl("/api/auth/logout") // REST-based logout
            .logoutSuccessHandler((request, response, authentication) -> {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Logout successful");
            })
            .permitAll()
        );

    return http.build();
}



//--> "Admin & Salesperson" käyttäjätiedot (UserDetailsService)?
       @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//--> Mitkä ovat järkevät käyttäjänimet/roolit ja mitä endpointteja niillä voi käsitellä?



//--> PasswordEncoder + Täytyy soveltaa luokkiin, jossa on salasanat esim. (Customer) => PasswordEncoder.encode(PasswordHash) tms.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("https://ticketguru-sovellus-elite-innovators-ticketguru2.2.rahtiapp.fi", "http://localhost:5173"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
}


}
  
   