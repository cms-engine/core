package com.ecommerce.engine.admin.service;

import com.ecommerce.engine.admin.annotation.SeoUrlRemove;
import com.ecommerce.engine.admin.dto.grid.PageGridDto;
import com.ecommerce.engine.admin.dto.request.PageRequestDto;
import com.ecommerce.engine.admin.dto.response.PageResponseDto;
import com.ecommerce.engine.entity.Page;
import com.ecommerce.engine.enums.SeoUrlType;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PageRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository repository;
    private final SearchService searchService;
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
        checkExisting(id);

        Page page = new Page(requestDto);
        page.setId(id);
        Page saved = repository.save(page);
        return new PageResponseDto(saved);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlType.PAGE)
    public void delete(long id) {
        foreignKeysChecker.checkUsages(Page.TABLE_NAME, id);
        repository.deleteById(id);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlType.PAGE)
    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Page.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Page findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Page.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(Page.TABLE_NAME, id);
        }
    }

    public SearchResponse<PageGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Page.class, PageGridDto::new);
    }
}
