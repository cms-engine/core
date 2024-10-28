package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Brand;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select id as value, name as label from Brand where :search is null or name like %:search%")
    List<SelectProjection> findSelectOptions(Pageable pageable, String search);
}
