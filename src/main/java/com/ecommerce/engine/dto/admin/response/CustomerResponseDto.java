package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.entity.Customer;
import java.time.Instant;

public record CustomerResponseDto(
        long id,
        Long customerGroupId,
        String firstName,
        String lastName,
        String middleName,
        String fullName,
        String phone,
        String email,
        boolean newsletter,
        Instant created,
        Instant updated,
        boolean enabled) {

    public CustomerResponseDto(Customer customer) {
        this(
                customer.getId(),
                customer.getCustomerGroupId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getMiddleName(),
                customer.getFullName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.isNewsletter(),
                customer.getCreated(),
                customer.getUpdated(),
                customer.isEnabled());
    }
}
