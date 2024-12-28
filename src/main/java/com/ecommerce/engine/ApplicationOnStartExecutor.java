package com.ecommerce.engine;

import com.ecommerce.engine._admin.entity.BackofficeUser;
import com.ecommerce.engine._admin.enumeration.Permission;
import com.ecommerce.engine._admin.repository.BackofficeUserRepository;
import com.ecommerce.engine.config.EngineProperties;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationOnStartExecutor {

    private final BackofficeUserRepository repository;
    private final EngineProperties engineProperties;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent _event) {
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
