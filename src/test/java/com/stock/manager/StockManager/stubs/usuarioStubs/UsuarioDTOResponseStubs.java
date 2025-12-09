package com.stock.manager.StockManager.stubs.usuarioStubs;

import com.stock.manager.StockManager.dto.request.UsuarioDTO;
import com.stock.manager.StockManager.dto.response.UsuarioDTOResponse;

public class UsuarioDTOResponseStubs {

    public static UsuarioDTOResponse createUsuarioDtoResponseStubs(){
        return UsuarioDTOResponse.builder()
                .name("Bruno")
                .build();
    }

}
