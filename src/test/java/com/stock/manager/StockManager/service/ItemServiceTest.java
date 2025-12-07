package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Eletronico;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import com.stock.manager.StockManager.repository.ItemRepository;
import com.stock.manager.StockManager.services.ItemServices;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOResponseStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServices itemServices;

    @Test
    void TestarCriarItem(){

        ReflectionTestUtils.setField(itemServices, "tipoEletronico", "ELETRONICO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTO();
        Item item = ItemStubs.createEletronico();
        ItemDTOResponse response = ItemDTOResponseStubs.createItemDTOResponse();

        when(fornecedorRepository.findById(1L))
                .thenReturn(Optional.of(FornecedorStubs.crateFornecedor()));

        when(itemMapper.dtoToEntityEletronico(itemDTO))
                .thenReturn((Eletronico) item);

        when(itemRepository.save(item))
                .thenReturn(item);

        when(itemMapper.entityToDto(item))
                .thenReturn(response);


        ItemDTOResponse result = itemServices.save("ELETRONICO", itemDTO);


        assertNotNull(result);
        assertEquals(response, result);
        assertEquals("Smartphone", result.getNome());
        verify(itemRepository).save(item);
        verify(fornecedorRepository).findById(1L);

    }

}
