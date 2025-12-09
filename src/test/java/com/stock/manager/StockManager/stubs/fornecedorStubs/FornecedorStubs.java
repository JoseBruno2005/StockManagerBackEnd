package com.stock.manager.StockManager.stubs.fornecedorStubs;

import com.stock.manager.StockManager.domain.Fornecedor;

public class FornecedorStubs {

    public static Fornecedor crateFornecedor(){
        return Fornecedor.builder()
                .id(1L)
                .nome("Fornecedor Teste")
                .telefone("999999999")
                .email("email@teste.com")
                .CNPJ("20.182.807/0001-42")
                .build();
    }

}
