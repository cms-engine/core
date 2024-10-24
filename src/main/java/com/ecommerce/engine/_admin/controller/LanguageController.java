package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.request.LanguageRequestDto;
import com.ecommerce.engine._admin.dto.response.LanguageResponseDto;
import com.ecommerce.engine._admin.service.LanguageService;
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
@RequestMapping("/admin/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/search")
    public SearchResponse<LanguageResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return languageService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public LanguageResponseDto get(@PathVariable int id) {
        return languageService.get(id);
    }

    @PostMapping
    public LanguageResponseDto create(@Valid @RequestBody LanguageRequestDto requestDto) {
        return languageService.save(requestDto);
    }

    @PutMapping("/{id}")
    public LanguageResponseDto update(@PathVariable int id, @Valid @RequestBody LanguageRequestDto requestDto) {
        return languageService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        languageService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Integer> ids) {
        languageService.deleteMany(ids);
    }
}
