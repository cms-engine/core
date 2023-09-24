package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.admin.grid.PageGridDto;
import com.ecommerce.engine.dto.admin.request.PageRequestDto;
import com.ecommerce.engine.dto.admin.response.PageResponseDto;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.service.PageService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping("/search/{id}")
    public SearchResponse<PageGridDto> getGridPageCache(@PathVariable UUID id) {
        return pageService.search(id, null);
    }

    @PostMapping("/search")
    public SearchResponse<PageGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return pageService.search(null, searchRequest);
    }

    @GetMapping("/{id}")
    public PageResponseDto get(@PathVariable long id) {
        return pageService.get(id);
    }

    @PostMapping
    public PageResponseDto create(@Valid @RequestBody PageRequestDto requestDto) {
        return pageService.save(requestDto);
    }

    @PutMapping("/{id}")
    public PageResponseDto update(@PathVariable long id, @Valid @RequestBody PageRequestDto requestDto) {
        return pageService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        pageService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        pageService.deleteMany(ids);
    }
}
