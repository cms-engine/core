package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.admin.response.CustomerResponseDto;
import com.ecommerce.engine.service.admin.CustomerService;
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
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/search")
    public SearchResponse<CustomerResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return customerService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto get(@PathVariable long id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable long id, @Valid @RequestBody CustomerRequestDto requestDto) {
        return customerService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        customerService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        customerService.deleteMany(ids);
    }
}
