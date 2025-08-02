package br.com.studiyng.crud.jz.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phone;
    private String email;
    private Boolean active;
    private List<AddressDTO> addresses = new ArrayList<>();


}
