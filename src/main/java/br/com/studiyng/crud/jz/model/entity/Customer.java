package br.com.studiyng.crud.jz.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length=11)
    private Integer cpf;
    @Column
    private Integer phone;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private Boolean active;
    // Relacionamento 1:N com Endereço
    @OneToMany(mappedBy = "costumer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses;

    public void enableCustomer() {
        this.active = true;
    }

    public void disableCustomer() {
        this.active = false;
    }

}
