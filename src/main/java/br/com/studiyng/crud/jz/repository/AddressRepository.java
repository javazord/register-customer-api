package br.com.studiyng.crud.jz.repository;

import br.com.studiyng.crud.jz.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
