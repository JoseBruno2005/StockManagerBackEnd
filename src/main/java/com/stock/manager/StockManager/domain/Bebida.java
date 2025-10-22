package com.stock.manager.StockManager.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Bebida")
@Data
public class Bebida extends Item{
    public Bebida(String nome, double preco, int quantidade, String foto, Fornecedor fornecedor) {
        super(nome, preco, quantidade, foto, fornecedor);
    }
}
