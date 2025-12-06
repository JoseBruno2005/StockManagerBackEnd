package com.stock.manager.StockManager.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTOResponse {
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String foto;
    private String categoria;
}
