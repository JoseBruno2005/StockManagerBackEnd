package com.stock.manager.StockManager.dto;

import com.stock.manager.StockManager.domain.Item;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
