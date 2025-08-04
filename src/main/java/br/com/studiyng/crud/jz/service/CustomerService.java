package br.com.studiyng.crud.jz.service;

import br.com.studiyng.crud.jz.exception.BusinessException;
import br.com.studiyng.crud.jz.exception.ResourceNotFoundException;
import br.com.studiyng.crud.jz.model.dto.AddressDTO;
import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Address;
import br.com.studiyng.crud.jz.model.entity.Customer;
import br.com.studiyng.crud.jz.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        boolean exists = customerRepository.existsCustomerByEmailOrCpf(customerDTO.getEmail(), customerDTO.getCpf());
        if (exists) {
            throw new BusinessException("Customer already exists with CPF or Email.");
        }
        Customer saved = customerRepository.save(customer);
        return mapToDTO(saved);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> lastTenCustomers = customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        Collections.reverse(lastTenCustomers);
        return lastTenCustomers;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        updateCustomerData(existingCustomer, customerDTO);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }

    public long countAllCustomers() {
        return customerRepository.count();
    }

    public List<CustomerDTO> getAllLastFiveCustomers() {
        List<CustomerDTO> lastTenCustomers = customerRepository.findTop5ByOrderByIdDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return lastTenCustomers;
    }

    public List<Customer> search(String cpf, String email) {
        Customer customer = new Customer();
        customer.setCpf(cpf);
        customer.setEmail(email);
        Example<Customer> example = Example.of(customer,
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return customerRepository.findAll(example);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setCpf(customer.getCpf());

        Address address = customer.getAddress();
        if (address != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(address.getId());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setDistrict(address.getDistrict());
            addressDTO.setCity(address.getCity());
            addressDTO.setState(address.getState());
            addressDTO.setZipCode(address.getZipCode());
            dto.setAddress(addressDTO);
        }

        return dto;
    }

    private Customer mapToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setDistrict(dto.getAddress().getDistrict());
            address.setZipCode(dto.getAddress().getZipCode());
            address.setCustomer(customer);

            customer.setAddress(address);
        }

        return customer;
    }

    private void updateCustomerData(Customer customer, CustomerDTO dto) {
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());

        Address address = customer.getAddress();

        if (address == null) {
            address = new Address();
            address.setCustomer(customer);
        }

        AddressDTO addressDTO = dto.getAddress();
        address.setStreet(addressDTO.getStreet());
        address.setState(addressDTO.getState());
        address.setCity(addressDTO.getCity());
        address.setDistrict(addressDTO.getDistrict());
        address.setZipCode(addressDTO.getZipCode());

        customer.setAddress(address);
    }

}
