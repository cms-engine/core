package com.ecommerce.engine.controller.store;

import com.ecommerce.engine.dto.common.ChangeCredentialsRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerInfoRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerRegisterRequestDto;
import com.ecommerce.engine.dto.store.response.CustomerInfoResponseDto;
import com.ecommerce.engine.service.store.CustomerService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerInfoResponseDto get(@PathVariable UUID id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public CustomerInfoResponseDto update(
            @PathVariable UUID id, @Valid @RequestBody CustomerInfoRequestDto requestDto) {
        return customerService.update(id, requestDto);
    }

    @PostMapping
    public CustomerInfoResponseDto register(@Valid @RequestBody CustomerRegisterRequestDto requestDto) {
        return customerService.register(requestDto);
    }

    @PostMapping("/{id}/credentials")
    public void changeCredentials(@PathVariable UUID id, @Valid @RequestBody ChangeCredentialsRequestDto requestDto) {
        customerService.changeCredentials(id, requestDto);
    }
}
