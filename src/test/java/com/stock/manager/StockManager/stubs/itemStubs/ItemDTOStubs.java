package com.stock.manager.StockManager.stubs.itemStubs;

import com.stock.manager.StockManager.dto.request.ItemDTO;

public class ItemDTOStubs {

    public static ItemDTO createItemDTOEletronico() {
        return ItemDTO.builder()
                .nome("Smartphone")
                .preco(2500.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedorId(1L)
                .build();
    }

    public static ItemDTO createItemDTOBebida() {
        return ItemDTO.builder()
                .nome("Coca Cola")
                .preco(10.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedorId(1L)
                .build();
    }

    public static ItemDTO createItemDTOAlimento() {
        return ItemDTO.builder()
                .nome("Arroz")
                .preco(5.0)
                .quantidade(10)
                .foto("foto-base64")
                .fornecedorId(1L)
                .build();
    }

    public static ItemDTO createItemDTOAEletronicoComNome() {
        return ItemDTO.builder()
                .nome("Arroz")
                .build();
    }

    public static ItemDTO createItemDTOEletronicoComPreco() {
        return ItemDTO.builder()
                .preco(2500.0)
                .build();
    }

    public static ItemDTO createItemDTOEletronicoComQuantidade() {
        return ItemDTO.builder()
                .quantidade(10)
                .build();
    }
    public static ItemDTO createItemDTOEletronicoComFoto() {
        return ItemDTO.builder()
                .nome("Arroz")
                .foto("foto-base64")
                .build();
    }

}
