package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.dto.request.ItemDTO;

public class ItemDTOStubs {

    public static ItemDTO createItemDTO() {
        return ItemDTO.builder()
                .nome("Smartphone")
                .preco(2500.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedorId(1L)
                .build();
    }

}
