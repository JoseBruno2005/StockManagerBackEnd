package com.stock.manager.StockManager.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransacaoDTOResponse {
    private int quantidade;
    private String tipoTransacao;
    private Date data;
    private Double valor;
    private Long itemId;
}
