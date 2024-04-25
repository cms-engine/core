package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.SeoUrl;
import com.ecommerce.engine.enums.SeoUrlType;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeoUrlRepository extends JpaRepository<SeoUrl, Long> {

    void deleteByTypeAndRecordId(SeoUrlType entity, long recordId);

    void deleteByTypeAndRecordIdIn(SeoUrlType entity, Collection<Long> recordIds);
}
