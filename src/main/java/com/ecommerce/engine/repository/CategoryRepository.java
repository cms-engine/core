package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            """
    select m.id as value, d.title as label from Category m
    join m.descriptions d on d.category.id = m.id and d.languageId = :languageId
    where :search is null or d.title like %:search% order by d.title
    """)
    List<SelectProjection> findSelectOptions(Pageable pageable, int languageId, String search);
}
