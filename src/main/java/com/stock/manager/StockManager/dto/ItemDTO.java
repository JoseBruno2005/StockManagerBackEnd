package com.stock.manager.StockManager.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String foto;
    private Long fornecedorId;
    private String categoria;
}
