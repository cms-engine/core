package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.PageGridDto;
import com.ecommerce.engine.dto.admin.request.PageRequestDto;
import com.ecommerce.engine.dto.admin.response.PageResponseDto;
import com.ecommerce.engine.entity.Page;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PageRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository repository;
    private final SearchService<Page, PageGridDto> searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public PageResponseDto get(long id) {
        Page page = findById(id);
        return new PageResponseDto(page);
    }

    public PageResponseDto save(PageRequestDto requestDto) {
        Page page = new Page(requestDto);
        Page saved = repository.save(page);
        return new PageResponseDto(saved);
    }

    public PageResponseDto update(long id, PageRequestDto requestDto) {
        Page existing = findById(id);

        Page page = new Page(requestDto);
        page.setId(id);
        page.getDescriptions().addAll(existing.getDescriptions());
        Page saved = repository.save(page);
        return new PageResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(Page.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Page.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Page findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Page.TABLE_NAME, id));
    }

    public SearchResponse<PageGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.PAGE, Page.class, PageGridDto::new);
    }
}
