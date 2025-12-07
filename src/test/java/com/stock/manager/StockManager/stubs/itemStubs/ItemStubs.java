package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.domain.Eletronico;
import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorStubs;

public class ItemStubs {

    public static Eletronico createEletronico() {
        return Eletronico.builder()
                .id(1L)
                .nome("Smartphone")
                .preco(2500.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedor(
                        FornecedorStubs.crateFornecedor()
                )
                .build();
    }

}
