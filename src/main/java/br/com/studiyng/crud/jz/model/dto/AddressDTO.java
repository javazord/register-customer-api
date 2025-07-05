package br.com.studiyng.crud.jz.model.dto;

import lombok.Data;
import br.com.studiyng.crud.jz.model.entity.Customer;

@Data
public class AddressDTO {

    private Long id;
    private String street;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private Customer customer;

}
