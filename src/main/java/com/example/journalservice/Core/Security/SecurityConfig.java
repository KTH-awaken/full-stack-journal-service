package com.example.journalservice.Core.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers("/conditions/{patientEmail}").hasAnyRole("DOCTOR", "EMPLOYEE")
                        .requestMatchers("/condition/{conditionId}").hasAnyRole("DOCTOR","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/condition").hasAnyRole("DOCTOR")
                        .requestMatchers("/encounter/{patientEmail}").hasAnyRole("DOCTOR","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/encounter").hasAnyRole("DOCTOR","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/encounter/observation").hasAnyRole("DOCTOR","EMPLOYEE")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }
}