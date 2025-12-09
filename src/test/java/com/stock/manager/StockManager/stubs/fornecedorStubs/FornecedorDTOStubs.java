package com.stock.manager.StockManager.stubs.fornecedorStubs;

import com.stock.manager.StockManager.dto.request.FornecedorDTO;

public class FornecedorDTOStubs {

    public static FornecedorDTO createFornecedorDTO(){
        return FornecedorDTO.builder()
                .id(1L)
                .nome("Fornecedor Teste")
                .telefone("999999999")
                .email("email@teste.com")
                .CNPJ("20.182.807/0001-42")
                .build();
    }

    public static FornecedorDTO updateNomeFornecedorDTO(){
        return FornecedorDTO.builder()
                .nome("Fornecedor Atualizado")
                .build();
    }

    public static FornecedorDTO updateTelefoneFornecedorDTO(){
        return FornecedorDTO.builder()
                .telefone("111111111")
                .build();
    }

    public static FornecedorDTO updateEmailFornecedorDTO(){
        return FornecedorDTO.builder()
                .email("email@atualizado.com")
                .build();
    }

    public static FornecedorDTO updateCNPJFornecedorDTO(){
        return FornecedorDTO.builder()
                .CNPJ("11.111.111/0001-11")
                .build();
    }


}
