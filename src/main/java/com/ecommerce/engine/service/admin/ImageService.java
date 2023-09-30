package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.config.EngineProperties;
import com.ecommerce.engine.dto.admin.response.ImageResponseDto;
import com.ecommerce.engine.entity.Image;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.ImageRepository;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import com.ecommerce.engine.validation.EntityType;
import jakarta.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService implements EntityPresenceService<UUID> {

    private final ImageRepository repository;
    private final ForeignKeysChecker foreignKeysChecker;
    private final String imageDir;

    @SneakyThrows
    public ImageService(
            ImageRepository repository, ForeignKeysChecker foreignKeysChecker, EngineProperties engineProperties) {
        this.repository = repository;
        this.imageDir = engineProperties.getImageDir();
        this.foreignKeysChecker = foreignKeysChecker;

        // Create the directory if it doesn't exist
        Path uploadPath = Paths.get(imageDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    public ImageResponseDto get(UUID id) {
        Image image = repository.findById(id).orElseThrow(() -> new NotFoundException(Image.TABLE_NAME, id));
        return new ImageResponseDto(image.getId(), image.getSrc(), image.getName());
    }

    public ImageResponseDto save(MultipartFile imageFile, String name) {
        UUID id = UUID.randomUUID();
        return saveAndGet(id, imageFile, name, null);
    }

    public ImageResponseDto update(UUID id, MultipartFile imageFile, String name) {
        String oldPath = repository.findById(id).map(Image::getSrc).orElse(null);
        return saveAndGet(id, imageFile, name, oldPath);
    }

    @SneakyThrows
    private ImageResponseDto saveAndGet(UUID id, MultipartFile imageFile, String name, @Nullable String oldPath) {
        // Extract the file extension from the original file name
        String fileExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());

        // Combine the unique filename and file extension
        String uniqueFileName = id + "-" + name + "." + fileExtension;

        // Build the path for the image file
        Path filePath = Paths.get(imageDir).resolve(uniqueFileName);

        if (oldPath != null) {
            Path oldFilePath = Paths.get(oldPath);
            if (!filePath.equals(oldFilePath) && Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
            }
        }

        // Save the image file to the server using Java NIO
        Files.copy(imageFile.getInputStream(), filePath);

        // Store the image reference in the database
        Image image = new Image(id, imageDir + "/" + uniqueFileName, name);
        Image saved = repository.save(image);

        return new ImageResponseDto(saved.getId(), saved.getSrc(), saved.getName());
    }

    public void delete(UUID id) {
        foreignKeysChecker.checkUsages(Image.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<UUID> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Image.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.IMAGE;
    }

    @Override
    public boolean exists(UUID id) {
        return repository.existsById(id);
    }
}
