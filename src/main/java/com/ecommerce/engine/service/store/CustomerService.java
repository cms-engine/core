package com.ecommerce.engine.service.store;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.dto.common.ChangeCredentialsRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerInfoRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerRegisterRequestDto;
import com.ecommerce.engine.dto.store.response.CustomerInfoResponseDto;
import com.ecommerce.engine.entity.Customer;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CustomerRepository;
import com.ecommerce.engine.util.StoreSettings;
import com.ecommerce.engine.util.TranslationUtils;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("customerServiceStore")
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerInfoResponseDto get(long id) {
        Customer customer = findById(id);
        return new CustomerInfoResponseDto(customer);
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

    public CustomerInfoResponseDto update(long id, CustomerInfoRequestDto requestDto) {
        Customer customer = findById(id);
        customer.update(requestDto);
        Customer saved = repository.save(customer);
        return new CustomerInfoResponseDto(saved);
    }

    private Customer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("customer", id));
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
