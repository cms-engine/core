package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.request.CustomerRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerInfoRequestDto;
import com.ecommerce.engine.dto.store.request.CustomerRegisterRequestDto;
import com.ecommerce.engine.util.NullUtils;
import io.github.lipiridi.searchengine.Searchable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = Customer.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    public static final String TABLE_NAME = "customer";

    @Searchable
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn
    CustomerGroup customerGroup;

    String firstName;
    String lastName;
    String middleName;
    String phone;

    @Column(unique = true)
    String email;

    String password;
    boolean newsletter;

    @CreationTimestamp
    Instant created;

    @UpdateTimestamp
    Instant updated;

    boolean enabled;

    public Customer(CustomerRegisterRequestDto requestDto, String encryptedPassword) {
        firstName = requestDto.firstName();
        lastName = requestDto.lastName();
        middleName = requestDto.middleName();
        phone = requestDto.phone();
        newsletter = requestDto.newsletter();
        email = requestDto.email();
        password = encryptedPassword;
        enabled = true;
    }

    public String getFullName() {
        String collected =
                Stream.of(firstName, lastName).filter(Objects::nonNull).collect(Collectors.joining(" "));

        return StringUtils.hasLength(collected) ? collected : null;
    }

    public Long getCustomerGroupId() {
        return NullUtils.nullable(customerGroup, CustomerGroup::getId);
    }

    public void update(CustomerRequestDto requestDto) {
        if (requestDto.customerGroupId() != null) {
            customerGroup = new CustomerGroup(requestDto.customerGroupId());
        }
        firstName = requestDto.firstName();
        lastName = requestDto.lastName();
        middleName = requestDto.middleName();
        phone = requestDto.phone();
        newsletter = requestDto.newsletter();
        enabled = requestDto.enabled();
    }

    public void update(CustomerInfoRequestDto requestDto) {
        firstName = requestDto.firstName();
        lastName = requestDto.lastName();
        middleName = requestDto.middleName();
        phone = requestDto.phone();
        newsletter = requestDto.newsletter();
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
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
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
