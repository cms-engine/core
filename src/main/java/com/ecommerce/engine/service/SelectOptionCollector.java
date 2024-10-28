package com.ecommerce.engine.service;

import com.ecommerce.engine.entity.projection.SelectProjection;
import com.ecommerce.engine.validation.EntityType;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SelectOptionCollector {

    EntityType getEntityType();

    List<SelectProjection> findSelectOptions(Pageable pageable, int languageId, @Nullable String search);
}
