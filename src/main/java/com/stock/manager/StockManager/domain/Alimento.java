package com.stock.manager.StockManager.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Alimento")
@Data
public class Alimento extends Item{
    public Alimento(String nome, double preco, int quantidade, String foto, Fornecedor fornecedor) {
        super(nome, preco, quantidade, foto, fornecedor);
    }
}
