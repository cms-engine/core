package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.common.StoreSettingDto;
import com.ecommerce.engine.util.StoreSettings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Locale;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@Table(name = "store_setting")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreSetting {

    @Id
    Integer id = 1;

    @Column(nullable = false)
    Locale adminLocale = Locale.US;

    @Column(nullable = false)
    Locale storeLocale = Locale.US;

    boolean allowAnonymousUsersToReviewProducts;

    boolean allowAnonymousUsersToReviewStore;

    boolean useCustomerGroups;

    Long customerGroupIdByDefault;

    @Column(nullable = false)
    String adminPasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    @Column(nullable = false)
    String storePasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    public void updateSettingsHolder() {
        StoreSettings.storeLocale = adminLocale;
        StoreSettings.adminLocale = storeLocale;
        StoreSettings.allowAnonymousUsersToReviewProducts = allowAnonymousUsersToReviewProducts;
        StoreSettings.allowAnonymousUsersToReviewStore = allowAnonymousUsersToReviewStore;
        StoreSettings.useCustomerGroups = useCustomerGroups;
        StoreSettings.customerGroupIdByDefault = customerGroupIdByDefault;
        StoreSettings.adminPasswordRegex = adminPasswordRegex;
        StoreSettings.storePasswordRegex = storePasswordRegex;
    }

    public void update(StoreSettingDto storeSettingDto) {
        adminLocale = storeSettingDto.adminLocale();
        storeLocale = storeSettingDto.storeLocale();
        allowAnonymousUsersToReviewProducts = storeSettingDto.allowAnonymousUsersToReviewProducts();
        allowAnonymousUsersToReviewStore = storeSettingDto.allowAnonymousUsersToReviewStore();
        useCustomerGroups = storeSettingDto.useCustomerGroups();
        customerGroupIdByDefault = storeSettingDto.customerGroupIdByDefault();
        adminPasswordRegex = storeSettingDto.adminPasswordRegex();
        storePasswordRegex = storeSettingDto.storePasswordRegex();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        StoreSetting that = (StoreSetting) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this)
                        .getHibernateLazyInitializer()
                        .getPersistentClass()
                        .hashCode()
                : getClass().hashCode();
    }
}
