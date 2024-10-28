package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.Image;
import com.ecommerce.engine.entity.projection.SelectProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("select id as value, name as label from Image where :search is null or name like %:search%")
    List<SelectProjection> findSelectOptions(Pageable pageable, String search);
}
