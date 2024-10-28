package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.CustomerGroup;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Long> {

    @Query(
            """
    select m.id as value, d.name as label from CustomerGroup m
    join m.descriptions d on d.customerGroup.id = m.id and d.languageId = :languageId
    where :search is null or d.name like %:search%
    """)
    List<SelectProjection> findSelectOptions(Pageable pageable, int languageId, String search);
}
