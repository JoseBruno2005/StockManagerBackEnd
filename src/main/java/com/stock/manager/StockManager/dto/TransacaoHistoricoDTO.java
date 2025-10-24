package com.stock.manager.StockManager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransacaoHistoricoDTO {
    private Long transacaoId;
    private String tipoTransacao;
    private Integer quantidade;
    private Double valor;
    private Date data;
}
