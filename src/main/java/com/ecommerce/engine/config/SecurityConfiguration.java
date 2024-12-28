package com.ecommerce.engine.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.ecommerce.engine._admin.entity.BackofficeUser;
import com.ecommerce.engine._admin.enumeration.Permission;
import com.ecommerce.engine._admin.repository.BackofficeUserRepository;
import jakarta.annotation.PostConstruct;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/store/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .rememberMe(withDefaults());

        return http.build();
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider(BackofficeUserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }*/

    @Bean
    public UserDetailsService userDetailsService(BackofficeUserRepository repository) {
        return username ->
                repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @PostConstruct
    public void init(
            BackofficeUserRepository repository, EngineProperties engineProperties, PasswordEncoder passwordEncoder) {
        EngineProperties.SystemUser systemUser = engineProperties.getSystemUser();
        if (repository.existsByUsernameAndIdNot(systemUser.username(), null)) {
            return;
        }

        BackofficeUser backofficeUser = new BackofficeUser();
        backofficeUser.setUsername(systemUser.username());
        backofficeUser.setPassword(passwordEncoder.encode(systemUser.password()));
        backofficeUser.setAuthorities(Set.of(Permission.USERS_WRITE));
        repository.save(backofficeUser);
    }
}
