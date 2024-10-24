package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.PageGridDto;
import com.ecommerce.engine._admin.dto.request.PageRequestDto;
import com.ecommerce.engine._admin.dto.response.PageResponseDto;
import com.ecommerce.engine._admin.service.PageService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
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
@RequestMapping("/admin/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @PostMapping("/search")
    public SearchResponse<PageGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return pageService.search(searchRequest);
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
