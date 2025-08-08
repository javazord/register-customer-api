package br.com.studiyng.crud.jz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String street;
    @Column
    private String district;
    @Column(length=20)
    private String city;
    @Column
    private String state;
    @Column(length=10)
    private String zipCode;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", unique = true, nullable = false)
    private Customer customer;

}
