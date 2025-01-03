package com.ecommerce.engine._admin.service;

import com.ecommerce.engine.enumeration.SeoUrlType;
import com.ecommerce.engine.repository.SeoUrlRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoUrlRemover {

    private final SeoUrlRepository seoUrlRepository;

    public void remove(SeoUrlType type, Long recordId) {
        seoUrlRepository.deleteByTypeAndRecordId(type, recordId);
    }

    public void remove(SeoUrlType entity, Set<Long> recordIds) {
        seoUrlRepository.deleteByTypeAndRecordIdIn(entity, recordIds);
    }
}
