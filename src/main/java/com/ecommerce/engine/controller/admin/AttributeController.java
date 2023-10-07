package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.dto.admin.grid.AttributeGridDto;
import com.ecommerce.engine.dto.admin.request.AttributeRequestDto;
import com.ecommerce.engine.dto.admin.response.AttributeResponseDto;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.service.admin.AttributeService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping("/search/{id}")
    public SearchResponse<AttributeGridDto> getGridPageCache(@PathVariable UUID id) {
        return attributeService.search(id, null);
    }

    @PostMapping("/search")
    public SearchResponse<AttributeGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return attributeService.search(null, searchRequest);
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

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        attributeService.deleteMany(ids);
    }
}
