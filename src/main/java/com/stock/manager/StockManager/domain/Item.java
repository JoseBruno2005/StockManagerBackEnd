package com.stock.manager.StockManager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categoria", discriminatorType = DiscriminatorType.STRING)
@SuperBuilder
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double preco;
    private Integer quantidade;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id",
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (fornecedor_id) REFERENCES " +
                            "fornecedor(id) ON DELETE SET NULL"
            )
    )
    private Fornecedor fornecedor;

    public Item(String nome, Double preco, Integer quantidade, String foto, Fornecedor fornecedor) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.foto = foto;
        this.fornecedor = fornecedor;
    }
}
