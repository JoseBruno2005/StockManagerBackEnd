package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Usuario;
import com.stock.manager.StockManager.dto.UsuarioDTO;
import com.stock.manager.StockManager.mapper.UsuarioMapper;
import com.stock.manager.StockManager.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    public UsuarioDTO save(UsuarioDTO usuarioDTO){
        try{
            Usuario usuario = usuarioMapper.dtoToEntity(usuarioDTO);
            return usuarioMapper.entityTodto(usuarioRepository.save(usuario));
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e) {
            throw new RuntimeException("Erro ao criar o usuario: " + e.getMessage(), e);
        }
    }

    public UsuarioDTO findByEmail(String email){
        return usuarioMapper.entityTodto(usuarioRepository.findByEmailUser(email));
    }
    public UsuarioDTO login(String email, String password){
        try{
            UsuarioDTO existingUser = findByEmail(email);

            if (existingUser == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
            if (!existingUser.getPassword().equals(password)) {
                throw new IllegalArgumentException("Senha invalida:");
            }
            return existingUser;
        }catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar login: " + e.getMessage(), e);
        }
    }
}
