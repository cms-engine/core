package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {}
