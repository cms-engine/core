package com.ecommerce.engine.service;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.admin.response.CustomerResponseDto;
import com.ecommerce.engine.dto.common.ChangeCredentialsRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerInfoRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerRegisterRequestDto;
import com.ecommerce.engine.dto.store.response.CustomerInfoResponseDto;
import com.ecommerce.engine.entity.Customer;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CustomerRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.util.TranslationUtils;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    public CustomerInfoResponseDto register(CustomerRegisterRequestDto requestDto) {
        checkEmailForUniqueness(requestDto.email());
        checkPasswordForRegexMatch(requestDto.password());

        // TODO encrypt password
        String encryptedPassword = requestDto.password();
        Customer customer = new Customer(requestDto, encryptedPassword);
        Customer saved = repository.save(customer);
        return new CustomerInfoResponseDto(saved);
    }

    public void changeCredentials(long id, ChangeCredentialsRequestDto requestDto) {
        Customer customer = findById(id);

        String email = requestDto.email();
        if (StringUtils.isNotBlank(email)) {
            checkEmailForUniqueness(email);
            customer.setEmail(email);
        }

        String password = requestDto.password();
        if (StringUtils.isNotBlank(password)) {
            checkPasswordForRegexMatch(password);

            // TODO encrypt password
            String encryptedPassword = requestDto.password();
            customer.setPassword(encryptedPassword);
        }

        repository.save(customer);
    }

    public CustomerResponseDto update(long id, CustomerRequestDto requestDto) {
        Customer customer = findById(id);
        customer.update(requestDto);
        Customer saved = repository.save(customer);
        return new CustomerResponseDto(saved);
    }

    public CustomerInfoResponseDto update(long id, CustomerInfoRequestDto requestDto) {
        Customer customer = findById(id);
        customer.update(requestDto);
        Customer saved = repository.save(customer);
        return new CustomerInfoResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Customer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("customer", id));
    }

    public SearchResponse<CustomerResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.CUSTOMER, Customer.class, CustomerResponseDto::new);
    }

    private void checkEmailForUniqueness(String email) {
        Optional<Customer> findByEmail = repository.findByEmail(email);
        if (findByEmail.isPresent()) {
            throw new ApplicationException(
                    ErrorCode.EMAIL_ALREADY_USED,
                    TranslationUtils.getMessage("exception.emailNotUnique").formatted(email));
        }
    }

    private void checkPasswordForRegexMatch(String password) {
        if (!Pattern.compile(StoreSettings.storePasswordRegex).matcher(password).matches()) {
            throw new ApplicationException(
                    ErrorCode.PASSWORD_IS_NOT_SAFE, TranslationUtils.getMessage("exception.passwordDoesntMatchRegex"));
        }
    }
}
