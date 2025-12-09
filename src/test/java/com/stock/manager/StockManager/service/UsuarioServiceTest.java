package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Usuario;
import com.stock.manager.StockManager.dto.request.UsuarioDTO;
import com.stock.manager.StockManager.dto.response.UsuarioDTOResponse;
import com.stock.manager.StockManager.mapper.UsuarioMapper;
import com.stock.manager.StockManager.repository.UsuarioRepository;
import com.stock.manager.StockManager.services.UsuarioService;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioDTOResponseStubs;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioDTOStubs;
import com.stock.manager.StockManager.stubs.usuarioStubs.UsuarioStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    void testarCriarUsuario(){
        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();
        Usuario usuario = UsuarioStubs.createUsuario();
        UsuarioDTOResponse usuarioDTOResponse = UsuarioDTOResponseStubs
                .createUsuarioDtoResponseStubs();

        when(usuarioMapper.dtoToEntity(usuarioDTO)).thenReturn(usuario);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        when(usuarioMapper.entityTodto(usuario)).thenReturn(usuarioDTOResponse);

        UsuarioDTOResponse result = usuarioService.save(usuarioDTO);

        assertNotNull(result);
        assertEquals(usuarioDTOResponse, result);
        assertEquals(usuarioDTOResponse.getName(), result.getName());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testarCriarUsuarioErroIllegalArgumentException(){
        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();

        when(usuarioMapper.dtoToEntity(usuarioDTO))
                .thenThrow(new IllegalArgumentException("Parametro Inválido"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {usuarioService.save(usuarioDTO);
                });

        assertTrue(exception.getMessage().contains("Parametro Inválido"));
        verify(usuarioMapper).dtoToEntity(usuarioDTO);
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).entityTodto(any());
    }

    @Test
    void testarCriarUsuarioErroRuntimeException(){
        UsuarioDTO usuarioDTO = UsuarioDTOStubs.createUsuarioDtoStubs();

        when(usuarioMapper.dtoToEntity(usuarioDTO))
                .thenThrow(new RuntimeException("Parametro Inválido"));

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {usuarioService.save(usuarioDTO);
                });

        assertTrue(exception.getMessage().contains("Erro ao criar o usuario: " + "Parametro Inválido"));
        verify(usuarioMapper).dtoToEntity(usuarioDTO);
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).entityTodto(any());
    }

    @Test
    void testarLogin() {
        Usuario usuario = UsuarioStubs.createUsuario();
        UsuarioDTOResponse usuarioResponse = UsuarioDTOResponseStubs.createUsuarioDtoResponseStubs();

        when(usuarioRepository.findByEmailUser(usuario.getEmail())).thenReturn(usuario);
        when(usuarioMapper.entityTodto(usuario)).thenReturn(usuarioResponse);

        UsuarioDTOResponse result = usuarioService.login(
                usuario.getEmail(), usuario.getPassword());

        assertNotNull(result);
        assertEquals(usuarioResponse, result);
        verify(usuarioRepository).findByEmailUser(usuario.getEmail());
        verify(usuarioMapper).entityTodto(usuario);
    }

    @Test
    void testarLoginErroEmailInvalido() {
        String email = "naoexiste@gmail.com";
        String password = "12345678";

        when(usuarioRepository.findByEmailUser(email)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.login(email, password);
        });

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));

        verify(usuarioRepository).findByEmailUser(email);
        verify(usuarioMapper, never()).entityTodto(any());
    }

    @Test
    void testarLoginErroSenhaInvalida() {
        String password = "senhaErrada";

        Usuario usuario = UsuarioStubs.createUsuario();
        when(usuarioRepository.findByEmailUser(usuario.getEmail())).thenReturn(usuario);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    usuarioService.login(usuario.getEmail(), password);
        });

        assertTrue(exception.getMessage().contains("Senha invalida"));

        verify(usuarioRepository).findByEmailUser(usuario.getEmail());
        verify(usuarioMapper, never()).entityTodto(any());
    }

    @Test
    void testarLoginErroCaiNoCatchException() {

        Usuario usuario = UsuarioStubs.createUsuario();

        when(usuarioRepository.findByEmailUser(usuario.getEmail()))
                .thenThrow(new RuntimeException("Falha desconhecida"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.login(usuario.getEmail(), usuario.getPassword());
        });

        assertEquals("Erro ao realizar login: Falha desconhecida",
                exception.getMessage());

        verify(usuarioRepository).findByEmailUser(usuario.getEmail());
        verify(usuarioMapper, never()).entityTodto(any());
    }

}
