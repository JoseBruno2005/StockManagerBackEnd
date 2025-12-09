package com.stock.manager.StockManager.stubs.transacaoStubs;

import com.stock.manager.StockManager.dto.request.TransacaoDTO;

import java.util.Date;

public class TransacaoDTOStubs {

    public static TransacaoDTO createTransacaoVendaDTOStubs(){
        return TransacaoDTO.builder()
                .quantidade(2)
                .tipoTransacao("venda")
                .data(new Date())
                .valor(5000.0)
                .itemId(1L)
                .build();
    }

    public static TransacaoDTO createTransacaoCompraDTOStubs(){
        return TransacaoDTO.builder()
                .quantidade(2)
                .tipoTransacao("compra")
                .data(new Date())
                .valor(5000.0)
                .itemId(1L)
                .build();
    }

}
