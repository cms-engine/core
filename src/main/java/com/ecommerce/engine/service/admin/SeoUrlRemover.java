package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.enums.SeoUrlEntity;
import com.ecommerce.engine.repository.SeoUrlRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoUrlRemover {

    private final SeoUrlRepository seoUrlRepository;

    public void remove(SeoUrlEntity entity, Long recordId) {
        seoUrlRepository.deleteByEntityAndRecordId(entity, recordId);
    }

    public void remove(SeoUrlEntity entity, Set<Long> recordIds) {
        seoUrlRepository.deleteByEntityAndRecordIdIn(entity, recordId);
    }
}
