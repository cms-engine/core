package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.CustomerIpId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_customer_ip")
@IdClass(CustomerIpId.class)
public class CustomerIp {

    @Id
    @ManyToOne
    @JoinColumn
    private Customer customer;
    @Id
    private String ip;
    private Boolean register;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerIp that = (CustomerIp) o;
        return customer != null && Objects.equals(customer, that.customer)
                && ip != null && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, ip);
    }
}
