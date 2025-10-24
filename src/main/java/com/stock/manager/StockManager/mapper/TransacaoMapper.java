package com.stock.manager.StockManager.mapper;

import com.stock.manager.StockManager.domain.*;
import com.stock.manager.StockManager.dto.TransacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {
    @Mapping(source = "itemId", target = "item", ignore = true)
    Transacao dtoToEntity(TransacaoDTO transacaoDTO);

    @Mapping(target = "itemId", expression = "java(transacao.getItem() != null ? transacao.getItem().getId() : null)")
    TransacaoDTO entityToDto(Transacao transacao);
}
