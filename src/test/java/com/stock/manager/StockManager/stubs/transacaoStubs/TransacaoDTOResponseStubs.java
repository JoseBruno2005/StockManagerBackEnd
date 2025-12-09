package com.stock.manager.StockManager.stubs.transacaoStubs;

import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;

import java.util.Date;

public class TransacaoDTOResponseStubs {

    public static TransacaoDTOResponse createTransacaoVendaDTOResponseStubs(){
        return TransacaoDTOResponse.builder()
                .quantidade(2)
                .tipoTransacao("venda")
                .data(new Date())
                .valor(5000.0)
                .itemId(1L)
                .build();
    }

    public static TransacaoDTOResponse createTransacaoCompraDTOResponseStubs(){
        return TransacaoDTOResponse.builder()
                .quantidade(2)
                .tipoTransacao("compra")
                .data(new Date())
                .valor(5000.0)
                .itemId(1L)
                .build();
    }

}
