package com.ecommerce.engine._admin.repository;

import com.ecommerce.engine._admin.entity.BackofficeUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BackofficeUserRepository extends JpaRepository<BackofficeUser, Long> {
    Optional<BackofficeUser> findByUsername(String username);

    @Query("select (count(b) > 0) from BackofficeUser b where b.username = :username and (:id is null or b.id <> :id)")
    boolean existsByUsernameAndIdNot(String username, Long id);
}
