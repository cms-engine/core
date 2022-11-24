package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.StoreStatusDescriptionId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_store_status_description")
@IdClass(StoreStatusDescriptionId.class)
public class StoreStatusDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private StoreStatus storeStatus;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StoreStatusDescription that = (StoreStatusDescription) o;
        return language != null && Objects.equals(language, that.language)
                && storeStatus != null && Objects.equals(storeStatus, that.storeStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, storeStatus);
    }
}
