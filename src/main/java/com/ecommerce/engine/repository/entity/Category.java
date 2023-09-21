package com.ecommerce.engine.repository.entity;

import static com.ecommerce.engine.util.NullUtils.nullable;

import com.ecommerce.engine.dto.request.CategoryRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    Category parent;

    @ManyToOne
    @JoinColumn
    Image image;

    int sortOrder;

    @CreationTimestamp
    Instant created;

    @UpdateTimestamp
    Instant updated;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<CategoryDescription> descriptions = new HashSet<>();

    public Category(CategoryRequestDto requestDto) {
        if (requestDto.parentId() != null) {
            parent = new Category(requestDto.parentId());
        }
        if (requestDto.imageId() != null) {
            image = new Image(requestDto.imageId());
        }
        sortOrder = requestDto.sortOrder();
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(CategoryDescription::new)
                .peek(desc -> desc.setCategory(this))
                .collect(Collectors.toSet());
    }

    public Category(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category that = (Category) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getLocaleTitle() {
        return Localable.getLocaleTitle(descriptions);
    }

    public Long getParentId() {
        return nullable(parent, Category::getId);
    }

    public String getParentLocaleTitle() {
        return nullable(parent, Category::getLocaleTitle);
    }

    public UUID getImageId() {
        return nullable(image, Image::getId);
    }

    public String getImageSrc() {
        return nullable(image, Image::getSrc);
    }
}
