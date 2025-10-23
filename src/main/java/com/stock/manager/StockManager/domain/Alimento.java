package com.stock.manager.StockManager.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Alimento")
@AllArgsConstructor
@Data
@SuperBuilder
public class Alimento extends Item{
    public Alimento(String nome, Double preco, Integer quantidade, String foto, Fornecedor fornecedor) {
        super(nome, preco, quantidade, foto, fornecedor);
    }
}
