package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.domain.Alimento;
import com.stock.manager.StockManager.domain.Bebida;
import com.stock.manager.StockManager.domain.Eletronico;
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

    public static Bebida createBebida() {
        return Bebida.builder()
                .id(1L)
                .nome("Coca Cola")
                .preco(10.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedor(
                        FornecedorStubs.crateFornecedor()
                )
                .build();
    }

    public static Alimento createAlimento() {
        return Alimento.builder()
                .id(1L)
                .nome("Arroz")
                .preco(5.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedor(
                        FornecedorStubs.crateFornecedor()
                )
                .build();
    }
}
