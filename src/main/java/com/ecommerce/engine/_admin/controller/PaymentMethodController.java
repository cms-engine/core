package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.PaymentMethodGridDto;
import com.ecommerce.engine._admin.dto.request.PaymentMethodRequestDto;
import com.ecommerce.engine._admin.dto.response.PaymentMethodResponseDto;
import com.ecommerce.engine._admin.service.PaymentMethodService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/search")
    public SearchResponse<PaymentMethodGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return paymentMethodService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public PaymentMethodResponseDto get(@PathVariable long id) {
        return paymentMethodService.get(id);
    }

    @PostMapping
    public PaymentMethodResponseDto create(@Valid @RequestBody PaymentMethodRequestDto requestDto) {
        return paymentMethodService.save(requestDto);
    }

    @PutMapping("/{id}")
    public PaymentMethodResponseDto update(
            @PathVariable long id, @Valid @RequestBody PaymentMethodRequestDto requestDto) {
        return paymentMethodService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        paymentMethodService.delete(id);
    }

    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        paymentMethodService.deleteMany(ids);
    }
}
