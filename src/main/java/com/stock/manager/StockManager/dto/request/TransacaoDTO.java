package com.stock.manager.StockManager.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransacaoDTO {

    private Long id;
    private int quantidade;
    private String tipoTransacao;
    private Date data;
    private Double valor;
    private Long itemId;

}
