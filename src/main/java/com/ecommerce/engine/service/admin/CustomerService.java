package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.admin.response.CustomerResponseDto;
import com.ecommerce.engine.entity.Customer;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository repository;
    private final SearchService<Customer, CustomerResponseDto> searchService;

    public CustomerResponseDto get(long id) {
        Customer customer = findById(id);
        return new CustomerResponseDto(customer);
    }

    public CustomerResponseDto update(long id, CustomerRequestDto requestDto) {
        Customer customer = findById(id);
        customer.update(requestDto);
        Customer saved = repository.save(customer);
        return new CustomerResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Customer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Customer.TABLE_NAME, id));
    }

    public SearchResponse<CustomerResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.CUSTOMER, Customer.class, CustomerResponseDto::new);
    }
}
