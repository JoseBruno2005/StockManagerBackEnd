package com.stock.manager.StockManager.mapper;

import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.dto.request.FornecedorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    @Mapping(target = "itens", ignore = true)
    Fornecedor dtoToEntity(FornecedorDTO fornecedorDTO);
    FornecedorDTO entityToDto(Fornecedor fornecedor);
}
