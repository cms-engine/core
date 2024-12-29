package com.ecommerce.engine.config;

import com.ecommerce.engine._admin.enumeration.Permission;
import com.ecommerce.engine._admin.repository.BackofficeUserRepository;
import com.ecommerce.engine.exception.handler.CommonAccessDeniedHandler;
import com.ecommerce.engine.exception.handler.UnauthorizedHandler;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableJdbcHttpSession
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UnauthorizedHandler unauthorizedHandler,
            CommonAccessDeniedHandler commonAccessDeniedHandler)
            throws Exception {
        http.authorizeHttpRequests(
                        request -> request.requestMatchers("/store/**")
                                .permitAll()
                                .requestMatchers("/actuator/**")
                                .hasAuthority(Permission.ACTUATOR.getAuthority())
                                .anyRequest()
                                .permitAll() // temporary
                        )
                .exceptionHandling(exHandling -> exHandling.accessDeniedHandler(commonAccessDeniedHandler))
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(unauthorizedHandler))
                .formLogin(formLogin -> formLogin.loginPage("/login.html").loginProcessingUrl("/login"))
                .rememberMe(rememberMe -> rememberMe.rememberMeServices(new SpringSessionRememberMeServices()))
                .csrf(AbstractHttpConfigurer::disable); // temporary

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(BackofficeUserRepository repository) {
        return username -> repository
                .findByUsername(username)
                .map(backofficeUser -> new User(
                        backofficeUser.getUsername(),
                        backofficeUser.getPassword(),
                        backofficeUser.isEnabled(),
                        true,
                        true,
                        true,
                        backofficeUser.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(null);
        configuration.setAllowedOriginPatterns(List.of(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
