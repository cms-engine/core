package com.ecommerce.engine.config;

import com.ecommerce.engine.model.entity.User;
import com.ecommerce.engine.model.entity.UserGroupPermission;
import com.ecommerce.engine.model.repo.UserGroupPermissionRepository;
import com.ecommerce.engine.model.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/register").anonymous()
                //We disabled it, because we allow to change myself
                //.antMatchers("/users/**").hasAuthority(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login-error").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository,
                                                 UserGroupPermissionRepository userGroupPermissionRepository) {
        return username -> {
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

            List<UserGroupPermission> userGroupPermissionList = userGroupPermissionRepository.findAllByUserGroup(user.getUserGroup());
            List<SimpleGrantedAuthority> grantedAuthorities = userGroupPermissionList
                    .stream()
                    .map(x -> new SimpleGrantedAuthority(x.getPermission().name()))
                    .toList();

            return new org.springframework.security.core.userdetails.User(
                    user.getId(), user.getPassword(), grantedAuthorities);
        };
    }

}
