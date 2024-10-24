package com.ecommerce.engine._store.dto.response;

import com.ecommerce.engine.entity.Customer;
import java.util.UUID;

public record CustomerInfoResponseDto(
        UUID id,
        Long customerGroupId,
        String firstName,
        String lastName,
        String middleName,
        String fullName,
        String phone,
        String email,
        boolean newsletter) {

    public CustomerInfoResponseDto(Customer customer) {
        this(
                customer.getId(),
                customer.getCustomerGroupId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getMiddleName(),
                customer.getFullName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.isNewsletter());
    }
}
