package com.ecommerce.engine.controller;

import com.ecommerce.engine.enums.SearchEntity;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import com.ecommerce.engine.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/{entity}/search")
    public SearchResponse getGridPage(@PathVariable SearchEntity entity, @RequestBody SearchRequest searchRequest) {
        return searchService.search(entity, searchRequest);
    }
}
