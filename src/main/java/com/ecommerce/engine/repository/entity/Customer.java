package com.ecommerce.engine.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn
    private CustomerGroup customerGroup;
    @OneToOne
    @JoinColumn
    private Language language;
    private String firstName;
    private String lastName;
    private String middleName;
    private String telephone;
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    private Boolean newsletter;
    @OneToOne
    @JoinColumn
    private DeliveryMethod mainDeliveryMethod;
    @OneToOne
    @JoinColumn
    private PaymentMethod mainPaymentMethod;
    @CreationTimestamp
    private LocalDate dateAdded;
    private Boolean status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer that = (Customer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
