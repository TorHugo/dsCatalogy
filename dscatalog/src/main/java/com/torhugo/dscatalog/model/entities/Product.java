package com.torhugo.dscatalog.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    private String imgUrl;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    @ManyToMany
    @JoinTable(
        name = "tb_product_category",
        joinColumns = @JoinColumn(name = "product_id"), // referencia a classe produto
        inverseJoinColumns = @JoinColumn(name = "category_id") // referencia a fk da tabela da associação
    ) // faz a associação entre as tabelas
    Set<Category> categories = new HashSet<>();
}
