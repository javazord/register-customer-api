package br.com.studiyng.crud.jz.service;

import br.com.studiyng.crud.jz.exception.BusinessException;
import br.com.studiyng.crud.jz.exception.ResourceNotFoundException;
import br.com.studiyng.crud.jz.model.dto.AddressDTO;
import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Address;
import br.com.studiyng.crud.jz.model.entity.Customer;
import br.com.studiyng.crud.jz.model.mapper.CustomerMapper;
import br.com.studiyng.crud.jz.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        boolean exists = customerRepository.existsCustomerByEmailOrCpf(customerDTO.getEmail(), customerDTO.getCpf());
        if (exists) {
            throw new BusinessException("Customer already exists with CPF or Email.");
        }
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDTO(saved);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customerMapper.toDTO(customers);
        Collections.reverse(customerDTOS);
        return customerDTOS;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        customerMapper.updateEntityFromDTO(customerDTO, existingCustomer);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDTO(updatedCustomer);
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
        List<Customer> customers = customerRepository.findTop5ByOrderByIdDesc();
        return customerMapper.toDTO(customers);
    }

    public List<CustomerDTO> search(String cpf, String email) {
        Customer customer = new Customer();

        customer.setCpf(cpf);
        customer.setEmail(email);
        Example<Customer> example = Example.of(customer,
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        List<Customer> customers = customerRepository.findAll(example);
        List<CustomerDTO> customerDTOs = customerMapper.toDTO(customers);
        Collections.reverse(customerDTOs);
        return customerDTOs;
    }


}
