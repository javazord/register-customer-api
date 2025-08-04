package br.com.studiyng.crud.jz.controller;

import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Customer;
import br.com.studiyng.crud.jz.model.mapper.CustomerMapper;
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

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/last-registers")
    public ResponseEntity<List<?>> getAllLastTenCustomers() {
        List<CustomerDTO> customers = customerService.getAllLastFiveCustomers();

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/count-customers")
    public ResponseEntity<?> countCustomer(){
        long count = customerService.countAllCustomers();
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam (value = "cpf", required = false) String cpf,
                                    @RequestParam (value = "email", required = false) String email){
        List<Customer> customers = customerService.search(cpf, email);
        List<CustomerDTO> customerDTOs = CustomerMapper.toDTOList(customers);
        return ResponseEntity.ok(customerDTOs);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
