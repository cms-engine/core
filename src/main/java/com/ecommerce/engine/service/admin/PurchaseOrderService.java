package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.admin.response.CustomerResponseDto;
import com.ecommerce.engine.entity.Customer;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CustomerRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final CustomerRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public CustomerResponseDto get(UUID id) {
        Customer customer = findById(id);
        return new CustomerResponseDto(customer);
    }

    public CustomerResponseDto update(UUID id, CustomerRequestDto requestDto) {
        Customer customer = findById(id);
        customer.update(requestDto);
        Customer saved = repository.save(customer);
        return new CustomerResponseDto(saved);
    }

    public void delete(UUID id) {
        foreignKeysChecker.checkUsages(Customer.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<UUID> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Customer.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Customer findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Customer.TABLE_NAME, id));
    }

    public SearchResponse<CustomerResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Customer.class, CustomerResponseDto::new);
    }
}
