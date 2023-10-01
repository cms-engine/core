package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.StoreSetting.TABLE_NAME;

import com.ecommerce.engine.config.EngineConfiguration;
import com.ecommerce.engine.dto.admin.common.StoreSettingDto;
import com.ecommerce.engine.util.StoreSettings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.LocaleUtils;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@Table(name = TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreSetting {

    public static final String TABLE_NAME = "store_setting";

    @Id
    Integer id = 1;

    String version = EngineConfiguration.APP_VERSION;

    String storeLocales = "en_US";

    Locale defaultStoreLocale = Locale.US;

    boolean allowAnonymousUsersToReviewProducts;

    boolean allowAnonymousUsersToReviewStore;

    boolean useCustomerGroups;

    Long customerGroupIdByDefault;

    @Column(nullable = false)
    String adminPasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    @Column(nullable = false)
    String storePasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    public void updateSettingsHolder() {
        StoreSettings.version = version;
        StoreSettings.storeLocales = getStoreLocales();
        StoreSettings.defaultStoreLocale = defaultStoreLocale;
        StoreSettings.allowAnonymousUsersToReviewProducts = allowAnonymousUsersToReviewProducts;
        StoreSettings.allowAnonymousUsersToReviewStore = allowAnonymousUsersToReviewStore;
        StoreSettings.useCustomerGroups = useCustomerGroups;
        StoreSettings.customerGroupIdByDefault = customerGroupIdByDefault;
        StoreSettings.adminPasswordRegex = adminPasswordRegex;
        StoreSettings.storePasswordRegex = storePasswordRegex;
    }

    public void update(StoreSettingDto storeSettingDto) {
        storeLocales =
                storeSettingDto.storeLocales().stream().map(Object::toString).collect(Collectors.joining(","));
        defaultStoreLocale = storeSettingDto.defaultStoreLocale();
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

    public List<Locale> getStoreLocales() {
        String[] localesArray = storeLocales.split(",");
        return Arrays.stream(localesArray).map(LocaleUtils::toLocale).toList();
    }
}
