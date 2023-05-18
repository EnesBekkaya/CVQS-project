package com.cvqs.securityservice.configuration;

import com.cvqs.securityservice.dto.UserSecurityDto;
import com.cvqs.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Returns a UserDetailsService bean that maps a username to a UserSecurityDto object using ModelMapper.
     * This bean is used to retrieve user information during the authentication process.
     * @return a UserDetailsService bean that maps a username to a UserSecurityDto object using ModelMapper
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> modelMapper.map(userRepository.findByUsername(username),UserSecurityDto.class);
    }

    /**
     * Returns a SecurityFilterChain bean that configures the security filter chain for the application's HTTP security.
     * This bean disables CSRF protection and permits access to specific endpoints without authentication.
     * @param http the HttpSecurity object to configure the security filter chain
     * @return a SecurityFilterChain bean that configures the security filter chain for the application's HTTP security
     * @throws Exception if an error occurs during the configuration process
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/authenticate", "/auth/validateToken", "/users/save","/terminals/**").permitAll()
                .and()
                .build();
    }

    /**
     * Returns an AuthenticationProvider bean that sets up a DaoAuthenticationProvider object with a UserDetailsService
     * and a PasswordEncoder. This bean is used to authenticate users during the authentication process.
     * @return an AuthenticationProvider bean that sets up a DaoAuthenticationProvider object with a UserDetailsService
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider  authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Returns an AuthenticationManager bean that retrieves the AuthenticationManager object from the provided
     * AuthenticationConfiguration. This bean is used to authenticate users during the authentication process.
     * @param config the AuthenticationConfiguration object containing the AuthenticationManager
     * @return an AuthenticationManager bean that retrieves the AuthenticationManager object from the provided
     * @throws Exception if an error occurs during the retrieval process
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

    /**
     * Returns a PasswordEncoder bean that sets up a BCryptPasswordEncoder object. This bean is used to encode and decode
     * passwords during the authentication process.
     * @return a PasswordEncoder bean that sets up a BCryptPasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
