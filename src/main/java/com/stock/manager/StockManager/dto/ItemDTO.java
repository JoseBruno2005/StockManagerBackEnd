package com.stock.manager.StockManager.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private Long id;
    private String nome;
    private double preco;
    private int quantidade;
    private String foto;
    private Long fornecedorId;
}
