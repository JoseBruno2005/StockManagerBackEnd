package com.stock.manager.StockManager.mapper;

import com.stock.manager.StockManager.domain.*;
import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {
    @Mapping(source = "itemId", target = "item", ignore = true)
    Transacao dtoToEntity(TransacaoDTO transacaoDTO);

    @Mapping(target = "itemId", expression = "java(transacao.getItem() != null ? transacao.getItem().getId() : null)")
    TransacaoDTOResponse entityToDto(Transacao transacao);
}
