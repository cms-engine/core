package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.response.ImageResponseDto;
import com.ecommerce.engine.service.ImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Valid
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ImageResponseDto get(@PathVariable UUID id) {
        return imageService.get(id);
    }

    @PostMapping
    public ImageResponseDto create(@RequestPart MultipartFile image, @RequestPart @NotBlank String name) {
        return imageService.save(image, name);
    }

    @PutMapping("/{id}")
    public ImageResponseDto update(
            @PathVariable UUID id, @RequestPart MultipartFile image, @RequestPart @NotBlank String name) {
        return imageService.update(id, image, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        imageService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<UUID> ids) {
        imageService.deleteMany(ids);
    }
}
