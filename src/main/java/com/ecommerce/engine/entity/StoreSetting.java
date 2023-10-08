package com.ecommerce.engine.entity;

import static com.ecommerce.engine.util.NullUtils.nullable;

import com.ecommerce.engine.config.EngineConfiguration;
import com.ecommerce.engine.dto.admin.common.StoreSettingDto;
import com.ecommerce.engine.util.StoreSettings;
import jakarta.persistence.*;
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
@Table(name = StoreSetting.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreSetting {

    public static final String TABLE_NAME = "store_setting";

    @Id
    Integer id = 1;

    boolean configured;

    @Column(nullable = false)
    String version = EngineConfiguration.APP_VERSION;

    @ManyToOne
    @JoinColumn
    Language defaultStoreLanguage;

    boolean useSubFoldersForMultiLanguageUrls; // domain.com/uk/, domain.com/en/

    boolean useSuffixesForMultiLanguageUrls; // domain.com/iphone-uk, domain.com/iphone-en

    boolean translateUrlForEachLanguage; // domain.com/biser (uk), domain.com/bead (en)

    boolean allowAnonymousUsersToReviewProducts;

    boolean allowAnonymousUsersToReviewStore;

    boolean useCustomerGroups;

    Long defaultCustomerGroupId;

    @Column(nullable = false)
    String adminPasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    @Column(nullable = false)
    String storePasswordRegex = "^(?=.*\\d)[^\\s]{8,}$";

    public void updateSettingsHolder() {
        StoreSettings.configured = configured;
        StoreSettings.version = version;
        StoreSettings.allowAnonymousUsersToReviewProducts = allowAnonymousUsersToReviewProducts;
        StoreSettings.allowAnonymousUsersToReviewStore = allowAnonymousUsersToReviewStore;
        StoreSettings.useCustomerGroups = useCustomerGroups;
        StoreSettings.defaultCustomerGroupId = defaultCustomerGroupId;
        StoreSettings.adminPasswordRegex = adminPasswordRegex;
        StoreSettings.storePasswordRegex = storePasswordRegex;

        if (defaultStoreLanguage == null) {
            StoreSettings.defaultStoreLocale = Locale.ENGLISH;
        } else {
            StoreSettings.defaultStoreLocale = defaultStoreLanguage.getHreflang();
            StoreSettings.defaultStoreLocaleId = defaultStoreLanguage.getId();
        }
    }

    public void update(StoreSettingDto storeSettingDto) {
        defaultStoreLanguage = new Language(storeSettingDto.defaultStoreLanguageId());
        allowAnonymousUsersToReviewProducts = storeSettingDto.allowAnonymousUsersToReviewProducts();
        allowAnonymousUsersToReviewStore = storeSettingDto.allowAnonymousUsersToReviewStore();
        useCustomerGroups = storeSettingDto.useCustomerGroups();
        defaultCustomerGroupId = storeSettingDto.defaultCustomerGroupId();
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

    public Integer getDefaultStoreLanguageId() {
        return nullable(defaultStoreLanguage, Language::getId);
    }
}
