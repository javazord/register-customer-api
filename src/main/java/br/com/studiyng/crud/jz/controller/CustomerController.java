package br.com.studiyng.crud.jz.controller;

import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO created = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

}
