package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.admin.response.CustomerResponseDto;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.service.admin.CustomerService;
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

    @GetMapping("/search/{id}")
    public SearchResponse<CustomerResponseDto> getGridPageCache(@PathVariable UUID id) {
        return customerService.search(id, null);
    }

    @PostMapping("/search")
    public SearchResponse<CustomerResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return customerService.search(null, searchRequest);
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
