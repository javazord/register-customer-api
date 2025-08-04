package br.com.studiyng.crud.jz.repository;

import br.com.studiyng.crud.jz.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getCustomerById(Long id);
    boolean existsCustomerByEmailOrCpf(String email, String cpf);
    List<Customer> findTop5ByOrderByIdDesc();
    @Override
    long count();
}
