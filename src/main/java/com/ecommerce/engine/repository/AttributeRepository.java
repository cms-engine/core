package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Attribute;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query(
            """
    select m.id as value, d.name as label from Attribute m
    join m.descriptions d on d.attribute.id = m.id and d.languageId = :languageId
    where :search is null or d.name like %:search% order by d.name
    """)
    List<SelectProjection> findSelectOptions(Pageable pageable, int languageId, String search);
}
