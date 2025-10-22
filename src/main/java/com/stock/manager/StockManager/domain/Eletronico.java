package com.stock.manager.StockManager.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("Eletronico")
@Data
public class Eletronico extends Item{
    public Eletronico(String nome, double preco, int quantidade, String foto, Fornecedor fornecedor) {
        super(nome, preco, quantidade, foto, fornecedor);
    }
}
