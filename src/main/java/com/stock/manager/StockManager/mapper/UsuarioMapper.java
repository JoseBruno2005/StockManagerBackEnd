package com.stock.manager.StockManager.mapper;

import com.stock.manager.StockManager.domain.Usuario;
import com.stock.manager.StockManager.dto.request.UsuarioDTO;
import com.stock.manager.StockManager.dto.response.UsuarioDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    Usuario dtoToEntity(UsuarioDTO usuarioDTO);
    UsuarioDTOResponse entityTodto(Usuario usuario);
}
