package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerIpId implements Serializable {

    private Customer customer;
    private String ip;

}
