package com.stock.manager.StockManager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstoqueReportDTO {
    private Long itemId;
    private String nomeItem;
    private Integer totalEntradas;
    private Integer totalSaidas;
    private Integer quantidadeAtual;
}
