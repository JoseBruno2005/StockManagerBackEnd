package com.stock.manager.StockManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.manager.StockManager.domain.Usuario;
import com.stock.manager.StockManager.dto.request.UsuarioDTO;
import com.stock.manager.StockManager.dto.response.UsuarioDTOResponse;
import com.stock.manager.StockManager.services.UsuarioService;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioDTOResponseStubs;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioDTOStubs;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioStubs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testarSalvarUsuario() throws Exception{
        Usuario usuario = UsuarioStubs.createUsuario();
        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();
        UsuarioDTOResponse usuarioDTOResponse = UsuarioDTOResponseStubs
                .createUsuarioDtoResponseStubs();

        when(usuarioService.save(usuarioDTO)).thenReturn(usuarioDTOResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(usuarioDTOResponse.getName()));

        verify(usuarioService).save(usuarioDTO);
    }

    @Test
    void testarLoginSucesso() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();
        UsuarioDTOResponse usuarioDTOResponse = UsuarioDTOResponseStubs.createUsuarioDtoResponseStubs();

        when(usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword()))
                .thenReturn(usuarioDTOResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(usuarioDTOResponse.getName()));

        verify(usuarioService).login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
    }

    @Test
    void testarLoginSenhaIncorreta() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();

        when(usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword()))
                .thenThrow(new IllegalArgumentException("Senha invalida:"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioDTO))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Senha invalida:"));

        verify(usuarioService).login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
    }

    @Test
    void testarLoginUsuarioNaoEncontrado() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();

        when(usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword()))
                .thenThrow(new IllegalArgumentException("Usuário não encontrado"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioDTO))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Usuário não encontrado"));

        verify(usuarioService).login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
    }

    @Test
    void testarLoginErroInterno() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();

        when(usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword()))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioDTO))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro inesperado"));

        verify(usuarioService).login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
    }

}
