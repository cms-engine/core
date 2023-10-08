package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.SeoUrl;
import com.ecommerce.engine.enums.SeoUrlEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeoUrlRepository extends JpaRepository<SeoUrl, Long> {

    void deleteByEntityAndRecordId(SeoUrlEntity entity, long recordId);

    void deleteByEntityAndRecordIdIn(SeoUrlEntity entity, Collection<Long> recordIds);
}
