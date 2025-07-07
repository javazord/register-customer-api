package br.com.studiyng.crud.jz.service;

import br.com.studiyng.crud.jz.model.dto.AddressDTO;
import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Address;
import br.com.studiyng.crud.jz.model.entity.Customer;
import br.com.studiyng.crud.jz.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapToDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setCpf(customerDTO.getCpf());
        // Remove old addresses and set new ones
        existingCustomer.getAddresses().clear();
        List<Address> newAddresses = customerDTO.getAddresses()
                .stream()
                .map(dto -> {
                    Address address = new Address();
                    address.setStreet(dto.getStreet());
                    address.setCity(dto.getCity());
                    address.setState(dto.getState());
                    address.setDistrict(dto.getDistrict());
                    address.setZipCode(dto.getZipCode());
                    address.setCustomer(existingCustomer);
                    return address;
                }).collect(Collectors.toList());
        existingCustomer.getAddresses().addAll(newAddresses);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToDTO(updatedCustomer);
    }

    public void enableCustomer(Long id) {
        Customer c = customerRepository.getCustomerById(id);
        c.enableCustomer();
        customerRepository.save(c);
    }

    public void disableCustomer(Long id) {
        Customer c = customerRepository.getCustomerById(id);
        c.disableCustomer();
        customerRepository.save(c);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setCpf(customer.getCpf());
        List<AddressDTO> addressDTOs = customer.getAddresses().stream().map(addr -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(addr.getId());
            addressDTO.setStreet(addr.getStreet());
            addressDTO.setDistrict(addr.getDistrict());
            addressDTO.setCity(addr.getCity());
            addressDTO.setState(addr.getState());
            addressDTO.setZipCode(addr.getZipCode());
            return addressDTO;
        }).collect(Collectors.toList());
        dto.setActive(true);
        dto.setAddresses(addressDTOs);
        return dto;
    }

    private Customer mapToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());
        List<Address> addresses = dto.getAddresses().stream().map(addressDTO -> {
            Address address = new Address();
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setDistrict(addressDTO.getDistrict());
            address.setZipCode(addressDTO.getZipCode());
            address.setCustomer(customer);
            return address;
        }).collect(Collectors.toList());
        customer.setActive(true);
        customer.setAddresses(addresses);
        return customer;
    }

}
