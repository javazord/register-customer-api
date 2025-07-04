package br.com.studiyng.crud.jz.repository;

import model.entity.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Long> {
}
