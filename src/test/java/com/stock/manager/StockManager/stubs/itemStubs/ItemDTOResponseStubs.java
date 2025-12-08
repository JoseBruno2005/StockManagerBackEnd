package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;

public class ItemDTOResponseStubs {

    public static ItemDTOResponse createItemDTOEletronicoResponse() {
        return ItemDTOResponse.builder()
                .id(1L)
                .nome("Smartphone")
                .preco(2500.0)
                .quantidade(10)
                .foto("foto-base64")
                .build();
    }

    public static ItemDTOResponse createItemDTOBebidaResponse() {
        return ItemDTOResponse.builder()
                .id(1L)
                .nome("Coca Cola")
                .preco(10.0)
                .quantidade(10)
                .foto("foto-base64")
                .build();
    }

    public static ItemDTOResponse createItemDTOAlimentoResponse() {
        return ItemDTOResponse.builder()
                .id(1L)
                .nome("Arroz")
                .preco(5.0)
                .quantidade(10)
                .foto("foto-base64")
                .build();
    }

    public static ItemDTOResponse createItemDTOEletronicoComQuantidade() {
        return ItemDTOResponse.builder()
                .quantidade(10)
                .build();
    }
}
