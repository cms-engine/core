package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = PurchaseOrderStatusDescription.TABLE_NAME)
@IdClass(PurchaseOrderStatusDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderStatusDescription extends NameDescriptionSuperclass {

    public static final String TABLE_NAME = "purchase_order_status_description";

    @Id
    @ManyToOne
    PurchaseOrderStatus purchaseOrderStatus;

    public PurchaseOrderStatusDescription(NameDescriptionDto descriptionDto) {
        super(descriptionDto);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Integer languageId;
        PurchaseOrderStatus purchaseOrderStatus;
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
        PurchaseOrderStatusDescription that = (PurchaseOrderStatusDescription) o;
        return getLanguageId() != null
                && Objects.equals(getLanguageId(), that.getLanguageId())
                && getPurchaseOrderStatus() != null
                && Objects.equals(getPurchaseOrderStatus(), that.getPurchaseOrderStatus());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getLanguageId(), getPurchaseOrderStatus());
    }
}
