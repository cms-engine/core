package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.request.BackofficeUserRequestDto;
import com.ecommerce.engine._admin.dto.request.BackofficeUserUpdateRequestDto;
import com.ecommerce.engine._admin.dto.response.BackofficeUserResponseDto;
import com.ecommerce.engine._admin.service.BackofficeUserService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class BackofficeUserController {

    private final BackofficeUserService backofficeUserService;

    @PostMapping("/search")
    public SearchResponse<BackofficeUserResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return backofficeUserService.search(searchRequest);
    }

    @PreAuthorize("hasAuthority(T(com.ecommerce.engine._admin.enumeration.Permission).ACTUATOR)")
    @GetMapping("/{id}")
    public BackofficeUserResponseDto get(@PathVariable long id) {
        return backofficeUserService.get(id);
    }

    @PreAuthorize("hasAuthority(T(com.ecommerce.engine._admin.enumeration.Permission).USERS_WRITE)")
    @PostMapping
    public BackofficeUserResponseDto create(@Valid @RequestBody BackofficeUserRequestDto requestDto) {
        return backofficeUserService.save(requestDto);
    }

    @PreAuthorize("hasAuthority(T(com.ecommerce.engine._admin.enumeration.Permission).USERS_WRITE)")
    @PutMapping("/{id}")
    public BackofficeUserResponseDto update(
            @PathVariable long id, @Valid @RequestBody BackofficeUserUpdateRequestDto requestDto) {
        return backofficeUserService.update(id, requestDto);
    }

    @PreAuthorize("hasAuthority(T(com.ecommerce.engine._admin.enumeration.Permission).USERS_WRITE)")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        backofficeUserService.delete(id);
    }

    @PreAuthorize("hasAuthority(T(com.ecommerce.engine._admin.enumeration.Permission).USERS_WRITE)")
    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        backofficeUserService.deleteMany(ids);
    }
}
