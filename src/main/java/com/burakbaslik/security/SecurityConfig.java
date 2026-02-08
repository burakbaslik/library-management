package com.burakbaslik.security;


import com.burakbaslik.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // --- CORS preflight ---
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // --- PUBLIC (anonim) ---
                        .requestMatchers(HttpMethod.GET,  "/auth/welcome").permitAll() // +
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/generateToken").permitAll() // +
                        .requestMatchers(HttpMethod.GET,  "/books", "/books/*").permitAll() // +
                        .requestMatchers(HttpMethod.GET,  "/authors", "/authors/*").permitAll() // +

                        // --- ADMIN ONLY: user management ---
                        .requestMatchers(HttpMethod.POST,   "/auth/addNewUser").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.GET,    "/auth/admin").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.GET,    "/auth/users", "/auth/users/*").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.DELETE, "/auth/delete/*").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.PUT,    "/auth/users/edit/*").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.PUT,    "/auth/edit/**").hasRole("ADMIN")

                        // --- ADMIN ONLY: book management ---
                        .requestMatchers(HttpMethod.POST,   "/books").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.PUT,    "/books/*").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.DELETE, "/books/*").hasRole("ADMIN") // +

                        // --- ADMIN ONLY: author management ---
                        .requestMatchers(HttpMethod.POST,   "/authors").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.PUT,    "/authors/*").hasRole("ADMIN") // +
                        .requestMatchers(HttpMethod.DELETE, "/authors/*").hasRole("ADMIN") // +

                        // --- AUTHENTICATED (ADMIN veya USER) ---
                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated() // +

                        // --- READ/FAVORITE LIST: ADMIN + USER erişebilsin ---
                        .requestMatchers("/read-list/**").hasAnyRole("ADMIN","USER") // - POST ve PUT işlemi yapamasın
                        .requestMatchers("/favorite-list/**").hasAnyRole("ADMIN","USER") // - POST ve PUT işlemi yapamasın

                        // --- (Opsiyonel) statik sayfalar / SPA girişleri ---
                        .requestMatchers("/", "/login", "/register", "/dashboard").permitAll()

                        // --- diğer her şey: kapat ya da authenticated yap ---
                        .anyRequest().denyAll()
                ).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}