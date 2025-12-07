package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.dto.response.ItemDTOResponse;

public class ItemDTOResponseStubs {

    public static ItemDTOResponse createItemDTOResponse() {
        return ItemDTOResponse.builder()
                .id(1L)
                .nome("Smartphone")
                .preco(2500.0)
                .quantidade(10)
                .foto("foto-base64")
                .build();
    }

}
