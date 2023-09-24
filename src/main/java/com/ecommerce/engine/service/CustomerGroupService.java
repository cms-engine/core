package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.CustomerGroupRequestDto;
import com.ecommerce.engine.dto.response.CustomerGroupResponseDto;
import com.ecommerce.engine.entity.CustomerGroup;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CustomerGroupRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerGroupService {

    private final CustomerGroupRepository repository;
    private final SearchService<CustomerGroup, CustomerGroupResponseDto> searchService;

    public CustomerGroupResponseDto get(long id) {
        CustomerGroup customerGroup = findById(id);
        return new CustomerGroupResponseDto(customerGroup);
    }

    public CustomerGroupResponseDto save(CustomerGroupRequestDto requestDto) {
        CustomerGroup customerGroup = new CustomerGroup(requestDto);
        CustomerGroup saved = repository.save(customerGroup);
        return new CustomerGroupResponseDto(saved);
    }

    public CustomerGroupResponseDto update(long id, CustomerGroupRequestDto requestDto) {
        findById(id);

        CustomerGroup customerGroup = new CustomerGroup(requestDto);
        customerGroup.setId(id);
        CustomerGroup saved = repository.save(customerGroup);
        return new CustomerGroupResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private CustomerGroup findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("customer group", id));
    }

    public SearchResponse<CustomerGroupResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(
                id, searchRequest, SearchEntity.CUSTOMER_GROUP, CustomerGroup.class, CustomerGroupResponseDto::new);
    }
}
