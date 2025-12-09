package com.stock.manager.StockManager.stubs.transacaoStubs;

import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.stubs.itemStubs.ItemStubs;

import java.util.Date;

public class TransacaoStubs {

    public static Transacao createTransacaoVendaStubs(){
        return Transacao.builder()
                .id(1L)
                .quantidade(2)
                .tipoTransacao("venda")
                .data(new Date())
                .valor(5000.0)
                .item(
                        ItemStubs.createEletronico()
                )
                .build();
    }

    public static Transacao createTransacaoCompraStubs(){
        return Transacao.builder()
                .id(1L)
                .quantidade(2)
                .tipoTransacao("compra")
                .data(new Date())
                .valor(5000.0)
                .item(
                        ItemStubs.createEletronico()
                )
                .build();
    }

}
