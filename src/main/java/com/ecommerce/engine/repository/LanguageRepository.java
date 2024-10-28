package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Language;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    Optional<Language> findByHreflang(Locale hreflang);

    Optional<Language> findByName(String name);

    Optional<Language> findBySubFolder(String subFolder);

    Optional<Language> findByUrlSuffix(String urlSuffix);

    List<Language> findAllByEnabledTrue();

    @Query("select id as value, name as label from Language where :search is null or name like %:search%")
    List<SelectProjection> findSelectOptions(Pageable pageable, String search);
}
