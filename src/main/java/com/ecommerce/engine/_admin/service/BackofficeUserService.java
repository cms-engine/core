package com.ecommerce.engine._admin.service;

import com.ecommerce.engine._admin.dto.request.BackofficeUserRequestDto;
import com.ecommerce.engine._admin.dto.request.BackofficeUserUpdateRequestDto;
import com.ecommerce.engine._admin.dto.response.BackofficeUserResponseDto;
import com.ecommerce.engine._admin.entity.BackofficeUser;
import com.ecommerce.engine._admin.repository.BackofficeUserRepository;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.exception.NotUniqueException;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.annotation.Nullable;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackofficeUserService {

    public static final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";

    private final BackofficeUserRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;
    private final PasswordEncoder passwordEncoder;

    public BackofficeUserResponseDto get(long id) {
        BackofficeUser backofficeUser = findById(id);
        return new BackofficeUserResponseDto(backofficeUser);
    }

    public BackofficeUserResponseDto save(BackofficeUserRequestDto requestDto) {
        checkUsernameDuplicate(requestDto.username(), null);

        BackofficeUser backofficeUser = new BackofficeUser();
        backofficeUser.setUsername(requestDto.username());
        backofficeUser.setAuthorities(requestDto.authorities());
        backofficeUser.setPassword(passwordEncoder.encode(requestDto.password()));

        BackofficeUser saved = repository.save(backofficeUser);
        return new BackofficeUserResponseDto(saved);
    }

    public BackofficeUserResponseDto update(long id, BackofficeUserUpdateRequestDto requestDto) {
        BackofficeUser backofficeUser = findById(id);
        checkUsernameDuplicate(requestDto.username(), id);

        backofficeUser.setUsername(requestDto.username());
        backofficeUser.setAuthorities(requestDto.authorities());
        backofficeUser.setEnabled(requestDto.enabled());
        if (StringUtils.isNotBlank(requestDto.password())) {
            backofficeUser.setPassword(passwordEncoder.encode(requestDto.password()));
        }

        BackofficeUser saved = repository.save(backofficeUser);
        return new BackofficeUserResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(BackofficeUser.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(BackofficeUser.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private BackofficeUser findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(BackofficeUser.TABLE_NAME, id));
    }

    private void checkUsernameDuplicate(String username, @Nullable Long id) {
        if (repository.existsByUsernameAndIdNot(username, id)) {
            throw new NotUniqueException(BackofficeUser.TABLE_NAME, username, "username", username);
        }
    }

    public SearchResponse<BackofficeUserResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, BackofficeUser.class, BackofficeUserResponseDto::new);
    }
}
