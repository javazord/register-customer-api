package model.entity;

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
    private String rua;
    @Column
    private String bairro;
    @Column(length=20)
    private String cidade;
    @Column(length=2)
    private String estado;
    @Column(length=10)
    private String cep;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

}
