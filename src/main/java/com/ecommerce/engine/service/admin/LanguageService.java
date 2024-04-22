package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.LanguageRequestDto;
import com.ecommerce.engine.dto.admin.response.LanguageResponseDto;
import com.ecommerce.engine.entity.CategoryDescription;
import com.ecommerce.engine.entity.CustomerGroupDescription;
import com.ecommerce.engine.entity.DeliveryMethodDescription;
import com.ecommerce.engine.entity.Language;
import com.ecommerce.engine.entity.PageDescription;
import com.ecommerce.engine.entity.PaymentMethodDescription;
import com.ecommerce.engine.entity.ProductDescription;
import com.ecommerce.engine.exception.ApplicationException;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.exception.NotUniqueException;
import com.ecommerce.engine.exception.handler.ErrorCode;
import com.ecommerce.engine.repository.LanguageRepository;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.util.TranslationUtils;
import com.ecommerce.engine.validation.EntityType;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@DependsOn("storeSettingService")
@Service
public class LanguageService implements EntityPresenceService<Integer> {

    private final LanguageRepository repository;
    private final SearchService searchService;
    private final EntityManager entityManager;

    public LanguageService(LanguageRepository repository, SearchService searchService, EntityManager entityManager) {
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
        Language saved = repository.save(language);

        fillStoreSettings();

        return new LanguageResponseDto(saved);
    }

    @Transactional
    public LanguageResponseDto update(int id, LanguageRequestDto requestDto) {
        findById(id);

        checkUniqueFields(requestDto);

        Language language = new Language(requestDto);
        language.setId(id);
        Language saved = repository.save(language);

        fillStoreSettings();

        return new LanguageResponseDto(saved);
    }

    @Transactional
    public void delete(int id) {
        Language existing = findById(id);

        if (StoreSettings.defaultStoreLocale.equals(existing.getHreflang())) {
            throw new ApplicationException(
                    ErrorCode.DEFAULT_ENTITY, TranslationUtils.getMessage("exception.cannotDeleteDefaultEntity"));
        }

        deleteAllDescriptions(id);
        repository.deleteById(id);
    }

    @Transactional
    public void deleteMany(Set<Integer> ids) {
        ids.forEach(this::delete);
    }

    private Language findById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Language.TABLE_NAME, id));
    }

    public SearchResponse<LanguageResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Language.class, LanguageResponseDto::new);
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
        StoreSettings.storeLocales = repository.findAllByEnabledTrue().stream()
                .collect(Collectors.toMap(Language::getId, Language::getHreflang));
    }

    private void deleteAllDescriptions(Integer languageId) {
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
                .createQuery("DELETE FROM %s lc WHERE lc.languageId = :languageId".formatted(className))
                .setParameter("languageId", languageId)
                .executeUpdate());
    }

    private void checkUniqueFields(LanguageRequestDto requestDto) {
        Optional<Language> byHreflang = repository.findByHreflang(requestDto.hreflang());
        if (byHreflang.isPresent()) {
            Language language = byHreflang.get();
            throw new NotUniqueException(Language.TABLE_NAME, language.getId(), "hreflang", language.getHreflang());
        }

        if (requestDto.subFolder() != null) {
            Optional<Language> bySubFolder = repository.findBySubFolder(requestDto.subFolder());
            if (bySubFolder.isPresent()) {
                Language language = bySubFolder.get();
                throw new NotUniqueException(
                        Language.TABLE_NAME, language.getId(), "subFolder", language.getSubFolder());
            }
        }

        if (requestDto.urlSuffix() != null) {
            Optional<Language> byUrlSuffix = repository.findByUrlSuffix(requestDto.urlSuffix());
            if (byUrlSuffix.isPresent()) {
                Language language = byUrlSuffix.get();
                throw new NotUniqueException(
                        Language.TABLE_NAME, language.getId(), "urlSuffix", language.getUrlSuffix());
            }
        }
    }
}
