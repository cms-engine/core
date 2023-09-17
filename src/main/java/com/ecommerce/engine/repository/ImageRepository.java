package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Image;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {}
