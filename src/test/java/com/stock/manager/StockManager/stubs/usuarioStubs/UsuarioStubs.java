package com.stock.manager.StockManager.stubs.usuarioStubs;

import com.stock.manager.StockManager.domain.Usuario;

public class UsuarioStubs {

    public static Usuario createUsuario(){
        return Usuario.builder()
                .id(1L)
                .name("Bruno")
                .email("bruno@gmail.com")
                .password("12345678")
                .build();
    }

}
