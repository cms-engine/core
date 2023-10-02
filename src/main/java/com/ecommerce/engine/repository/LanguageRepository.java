package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Language;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    Optional<Language> findByHreflang(Locale hreflang);

    Optional<Language> findBySubFolder(String subFolder);

    Optional<Language> findBySuffixUrl(String suffixUrl);

    Optional<Language> findByDefaultLangTrue();
}
