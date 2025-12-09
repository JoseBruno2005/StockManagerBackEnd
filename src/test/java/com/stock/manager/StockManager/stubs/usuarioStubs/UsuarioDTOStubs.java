package com.stock.manager.StockManager.stubs.usuarioStubs;

import com.stock.manager.StockManager.dto.request.UsuarioDTO;

public class UsuarioDTOStubs {

    public static UsuarioDTO createUsuarioDtoStubs(){
        return UsuarioDTO.builder()
                .name("Bruno")
                .email("bruno@gmail.com")
                .password("12345678")
                .build();
    }

}
