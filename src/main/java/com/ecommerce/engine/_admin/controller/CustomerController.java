package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.request.CustomerRequestDto;
import com.ecommerce.engine._admin.dto.response.CustomerResponseDto;
import com.ecommerce.engine._admin.service.CustomerService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
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
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/search")
    public SearchResponse<CustomerResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return customerService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto get(@PathVariable UUID id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable UUID id, @Valid @RequestBody CustomerRequestDto requestDto) {
        return customerService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        customerService.delete(id);
    }

    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<UUID> ids) {
        customerService.deleteMany(ids);
    }
}
