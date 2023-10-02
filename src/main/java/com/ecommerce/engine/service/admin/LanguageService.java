package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.LanguageRequestDto;
import com.ecommerce.engine.dto.admin.response.LanguageResponseDto;
import com.ecommerce.engine.entity.Language;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.LanguageRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import com.ecommerce.engine.validation.EntityType;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService implements EntityPresenceService<Integer> {

    private final LanguageRepository repository;
    private final SearchService<Language, LanguageResponseDto> searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public LanguageResponseDto get(int id) {
        Language language = findById(id);
        return new LanguageResponseDto(language);
    }

    public LanguageResponseDto save(LanguageRequestDto requestDto) {
        Language language = new Language(requestDto);
        Language saved = repository.save(language);
        return new LanguageResponseDto(saved);
    }

    public LanguageResponseDto update(int id, LanguageRequestDto requestDto) {
        findById(id);

        Language language = new Language(requestDto);
        language.setId(id);
        Language saved = repository.save(language);
        return new LanguageResponseDto(saved);
    }

    public void delete(int id) {
        foreignKeysChecker.checkUsages(Language.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Integer> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Language.TABLE_NAME, id));
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
}
