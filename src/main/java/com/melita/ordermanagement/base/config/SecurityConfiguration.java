package com.melita.ordermanagement.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * @author sorokus.dev@gmail.com
 */
// Basic Auth is used for simplicity and demo purposes only.
// Change to OAuth, JWT etc. for production use.
@Configuration
public class SecurityConfiguration {

    @Value("${app.security.client.username}")
    private String clientUsername;

    @Value("${app.security.client.password}")
    private String clientPassword;

    @Value("${app.security.agent.username}")
    private String agentUsername;

    @Value("${app.security.agent.password}")
    private String agentPassword;

    @Bean
    // Simple Storage and `InMemoryUserDetailsManager` used for simplicity and demo purposes only.
    // Change to Relational Databases, Custom data store, LDAP store etc. for production use.
    public InMemoryUserDetailsManager userDetailsService() {
        // Remark: We are aware `DefaultPasswordEncoder` is unsafe. Used for simplicity and demo purposes only.
        // Change for production.
        @SuppressWarnings("deprecation")
        UserDetails clintUserDetails = User.withDefaultPasswordEncoder()
                                           .username(clientUsername)
                                           .password(clientPassword)
                                           .roles("USER")
                                           .build();
        @SuppressWarnings("deprecation")
        UserDetails agentUserDetails = User.withDefaultPasswordEncoder()
                                           .username(agentUsername)
                                           .password(agentPassword)
                                           .roles("AGENT")
                                           .build();
        return new InMemoryUserDetailsManager(clintUserDetails, agentUserDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                   .disable()
                   .authorizeHttpRequests()
                   .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/configuration/ui","/configuration/security")
                    .permitAll()
//                   .and().cors()
                   .and()
                   .authorizeHttpRequests()
                   .requestMatchers("/api/v1/ordermanagement/placement/**")
                   .hasRole("USER")
                   .and()
                   .authorizeHttpRequests()
                   .requestMatchers("/api/v1/ordermanagement/approval/**")
                   .hasRole("AGENT")
                   .and()
                   .httpBasic()
                   .and()
                   .build();
    }

}