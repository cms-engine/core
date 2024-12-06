package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.AttributeGridDto;
import com.ecommerce.engine._admin.dto.request.AttributeRequestDto;
import com.ecommerce.engine._admin.dto.response.AttributeResponseDto;
import com.ecommerce.engine._admin.service.AttributeService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @PostMapping("/search")
    public SearchResponse<AttributeGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return attributeService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public AttributeResponseDto get(@PathVariable long id) {
        return attributeService.get(id);
    }

    @PostMapping
    public AttributeResponseDto create(@Valid @RequestBody AttributeRequestDto requestDto) {
        return attributeService.save(requestDto);
    }

    @PutMapping("/{id}")
    public AttributeResponseDto update(@PathVariable long id, @Valid @RequestBody AttributeRequestDto requestDto) {
        return attributeService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        attributeService.delete(id);
    }

    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        attributeService.deleteMany(ids);
    }
}
