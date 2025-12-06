package com.stock.manager.StockManager.dto.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class FornecedorDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String CNPJ;
}
