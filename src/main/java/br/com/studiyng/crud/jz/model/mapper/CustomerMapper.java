package br.com.studiyng.crud.jz.model.mapper;

import br.com.studiyng.crud.jz.model.dto.AddressDTO;
import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getLastName());
        dto.setCpf(customer.getCpf());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(customer.getAddress().getId());
        addressDTO.setStreet(customer.getAddress().getStreet());
        addressDTO.setCity(customer.getAddress().getCity());
        addressDTO.setState(customer.getAddress().getState());
        addressDTO.setDistrict(customer.getAddress().getDistrict());
        addressDTO.setZipCode(customer.getAddress().getZipCode());

        dto.setAddress(addressDTO);
        return dto;
    }

    public static List<CustomerDTO> toDTOList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

}
