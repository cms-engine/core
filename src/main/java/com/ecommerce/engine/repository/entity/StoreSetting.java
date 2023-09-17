package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.util.StoreSettings;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "store_setting")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreSetting {

    @Id
    Integer id = 1;

    Locale adminLocale = new Locale("en", "US");

    Locale storeLocale = new Locale("en", "US");

    public void updateSettingsHolder() {
        StoreSettings.storeLocale = adminLocale;
        StoreSettings.adminLocale = storeLocale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StoreSetting that = (StoreSetting) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
