package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.dto.admin.request.LanguageRequestDto;
import com.ecommerce.engine.dto.admin.response.LanguageResponseDto;
import com.ecommerce.engine.entity.CategoryDescription;
import com.ecommerce.engine.entity.CustomerGroupDescription;
import com.ecommerce.engine.entity.DeliveryMethodDescription;
import com.ecommerce.engine.entity.Language;
import com.ecommerce.engine.entity.PageDescription;
import com.ecommerce.engine.entity.PaymentMethodDescription;
import com.ecommerce.engine.entity.ProductDescription;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.exception.NotUniqueException;
import com.ecommerce.engine.repository.LanguageRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.util.TranslationUtils;
import com.ecommerce.engine.validation.EntityType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LanguageService implements EntityPresenceService<Integer> {

    private final LanguageRepository repository;
    private final SearchService<Language, LanguageResponseDto> searchService;
    private final EntityManager entityManager;

    public LanguageService(
            LanguageRepository repository,
            SearchService<Language, LanguageResponseDto> searchService,
            EntityManager entityManager) {
        this.repository = repository;
        this.searchService = searchService;
        this.entityManager = entityManager;

        fillStoreSettings();
    }

    public LanguageResponseDto get(int id) {
        Language language = findById(id);
        return new LanguageResponseDto(language);
    }

    @Transactional
    public LanguageResponseDto save(LanguageRequestDto requestDto) {
        checkUniqueFields(requestDto);

        Language language = new Language(requestDto);
        if (repository.count() == 0) {
            language.setDefaultLang(true);
        }
        Language saved = repository.save(language);

        fillStoreSettings();

        return new LanguageResponseDto(saved);
    }

    @Transactional
    public LanguageResponseDto update(int id, LanguageRequestDto requestDto) {
        Language existing = findById(id);

        checkUniqueFields(requestDto);

        Language language = new Language(requestDto);
        language.setId(id);
        language.setDefaultLang(existing.isDefaultLang());
        Language saved = repository.save(language);

        fillStoreSettings();

        return new LanguageResponseDto(saved);
    }

    public LanguageResponseDto makeDefault(int id) {
        Language existing = findById(id);

        existing.setEnabled(true);

        Optional<Language> byDefaultLangTrue = repository.findByDefaultLangTrue();
        if (byDefaultLangTrue.isPresent()) {
            Language foundDefaultLanguage = byDefaultLangTrue.get();
            if (existing.equals(foundDefaultLanguage)) {
                return new LanguageResponseDto(existing);
            }

            foundDefaultLanguage.setDefaultLang(false);
            repository.save(foundDefaultLanguage);
        }

        existing.setDefaultLang(true);
        repository.save(existing);

        fillStoreSettings();

        return new LanguageResponseDto(existing);
    }

    @Transactional
    public void delete(int id) {
        if (StoreSettings.defaultStoreLanguage.getId().equals(id)) {
            throw new ApplicationException(
                    ErrorCode.DEFAULT_ENTITY, TranslationUtils.getMessage("exception.cannotDeleteDefaultEntity"));
        }

        deleteAllDescriptions(Set.of(id));
        repository.deleteById(id);
    }

    @Transactional
    public void deleteMany(Set<Integer> ids) {
        if (ids.stream()
                .anyMatch(id -> StoreSettings.defaultStoreLanguage.getId().equals(id))) {
            throw new ApplicationException(
                    ErrorCode.DEFAULT_ENTITY, TranslationUtils.getMessage("exception.cannotDeleteDefaultEntity"));
        }

        deleteAllDescriptions(ids);
        repository.deleteAllById(ids);
    }

    private Language findById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Language.TABLE_NAME, id));
    }

    public SearchResponse<LanguageResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.LANGUAGE, Language.class, LanguageResponseDto::new);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LANGUAGE;
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    private void fillStoreSettings() {
        Optional<Language> byDefaultLangTrue = repository.findByDefaultLangTrue();
        if (byDefaultLangTrue.isPresent()) {
            StoreSettings.defaultStoreLanguage = byDefaultLangTrue.get();
        } else {
            Language language = new Language();
            language.setHreflang(Locale.ENGLISH);
            StoreSettings.defaultStoreLanguage = language;
        }

        StoreSettings.storeLanguages = repository.findAllByEnabledTrue();
    }

    private void deleteAllDescriptions(Set<Integer> languageIds) {
        Set<Class<?>> entityClassesWithLanguage = Set.of(
                CategoryDescription.class,
                CustomerGroupDescription.class,
                DeliveryMethodDescription.class,
                PageDescription.class,
                PaymentMethodDescription.class,
                ProductDescription.class);

        Set<String> classNames =
                entityClassesWithLanguage.stream().map(Class::getSimpleName).collect(Collectors.toSet());

        classNames.forEach(className -> entityManager
                .createQuery("DELETE FROM %s lc WHERE lc.language.id in :languageIds".formatted(className))
                .setParameter("languageIds", languageIds)
                .executeUpdate());
    }

    private void checkUniqueFields(LanguageRequestDto requestDto) {
        Optional<Language> byHreflang = repository.findByHreflang(requestDto.hreflang());
        if (byHreflang.isPresent()) {
            Language language = byHreflang.get();
            throw new NotUniqueException(Language.TABLE_NAME, language.getId(), "hreflang", language.getHreflang());
        }

        Optional<Language> bySubFolder = repository.findBySubFolder(requestDto.subFolder());
        if (bySubFolder.isPresent()) {
            Language language = bySubFolder.get();
            throw new NotUniqueException(Language.TABLE_NAME, language.getId(), "subFolder", language.getHreflang());
        }

        Optional<Language> byUrlSuffix = repository.findByUrlSuffix(requestDto.urlSuffix());
        if (byUrlSuffix.isPresent()) {
            Language language = byUrlSuffix.get();
            throw new NotUniqueException(Language.TABLE_NAME, language.getId(), "urlSuffix", language.getHreflang());
        }
    }
}
