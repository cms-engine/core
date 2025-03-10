package com.ecommerce.engine.config;

import static com.ecommerce.engine.config.MvcConfig.IMAGES_PATTERN;

import com.ecommerce.engine._admin.enumeration.Permission;
import com.ecommerce.engine._admin.repository.BackofficeUserRepository;
import com.ecommerce.engine.exception.handler.CommonAccessDeniedHandler;
import com.ecommerce.engine.exception.handler.UnauthorizedHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UnauthorizedHandler unauthorizedHandler,
            CommonAccessDeniedHandler commonAccessDeniedHandler)
            throws Exception {
        return http.securityMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher(IMAGES_PATTERN)))
                .authorizeHttpRequests(request -> request.requestMatchers("/store/**", "/favicon.ico")
                        .permitAll()
                        .requestMatchers("/actuator/**")
                        .hasAuthority(Permission.ACTUATOR.getAuthority())
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(exHandling -> exHandling.accessDeniedHandler(commonAccessDeniedHandler))
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(unauthorizedHandler))
                .formLogin(Customizer.withDefaults())
                .rememberMe(rememberMe -> rememberMe.rememberMeServices(new SpringSessionRememberMeServices()))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public SecurityFilterChain cacheFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher(IMAGES_PATTERN)
                .authorizeHttpRequests(
                        request -> request.requestMatchers(IMAGES_PATTERN).permitAll())
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .build();
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
    @Profile("fedev")
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:[*]"));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
