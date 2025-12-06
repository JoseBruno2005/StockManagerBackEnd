package com.stock.manager.StockManager.mapper;

import com.stock.manager.StockManager.domain.Alimento;
import com.stock.manager.StockManager.domain.Bebida;
import com.stock.manager.StockManager.domain.Eletronico;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "fornecedorId", target = "fornecedor", ignore = true)
    Alimento dtoToEntityAlimento(ItemDTO itemDTO);

    @Mapping(source = "fornecedorId", target = "fornecedor", ignore = true)
    Bebida dtoToEntityBebida(ItemDTO itemDTO);

    @Mapping(source = "fornecedorId", target = "fornecedor", ignore = true)
    Eletronico dtoToEntityEletronico(ItemDTO itemDTO);

    @Mapping(target = "categoria", expression = "java(item.getClass().getAnnotation(jakarta.persistence.DiscriminatorValue.class).value())")
    ItemDTOResponse entityToDto(Item item);
}
