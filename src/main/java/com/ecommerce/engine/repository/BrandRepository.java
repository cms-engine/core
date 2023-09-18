package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {}
