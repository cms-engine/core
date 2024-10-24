package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.entity.Customer;
import java.time.Instant;
import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        Long customerGroupId,
        String firstName,
        String lastName,
        String middleName,
        String fullName,
        String phone,
        String email,
        boolean newsletter,
        Instant createdAt,
        Instant updatedAt,
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
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isEnabled());
    }
}
