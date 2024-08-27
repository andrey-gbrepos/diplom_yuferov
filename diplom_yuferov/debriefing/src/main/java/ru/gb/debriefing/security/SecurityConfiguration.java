package ru.gb.debriefing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.debriefing.repository.RoleRepository;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            RoleRepository roleRepository) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/students/**").hasAuthority("doclogist")
                        .requestMatchers("/reports/**").hasAuthority("pilot")
                        .requestMatchers("/planes/**").hasAuthority("engineer")
                        .requestMatchers("/pilots/**", "/positions/**").hasAuthority("commandor")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
