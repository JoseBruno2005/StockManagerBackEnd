package com.stock.manager.StockManager.stubs.fornecedorStubs;

import com.stock.manager.StockManager.domain.Fornecedor;

public class FornecedorStubs {

    public static Fornecedor crateFornecedor(){
        return Fornecedor.builder()
                .id(1L)
                .nome("Fornecedor Teste")
                .email("email@teste.com")
                .telefone("999999999")
                .build();
    }

}
