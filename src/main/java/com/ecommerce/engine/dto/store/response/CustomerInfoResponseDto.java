package com.ecommerce.engine.dto.store.response;

import com.ecommerce.engine.entity.Customer;

public record CustomerInfoResponseDto(
        long id,
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
