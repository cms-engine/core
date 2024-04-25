package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.enums.SeoUrlType;
import com.ecommerce.engine.repository.SeoUrlRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoUrlRemover {

    private final SeoUrlRepository seoUrlRepository;

    public void remove(SeoUrlType type, Long recordId) {
        seoUrlRepository.deleteByEntityAndRecordId(type, recordId);
    }

    public void remove(SeoUrlType entity, Set<Long> recordIds) {
        seoUrlRepository.deleteByEntityAndRecordIdIn(entity, recordIds);
    }
}
