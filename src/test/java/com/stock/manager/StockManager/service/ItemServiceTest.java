package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Alimento;
import com.stock.manager.StockManager.domain.Bebida;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

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
    void TestarCriarItemEletronico(){

        ReflectionTestUtils.setField(itemServices, "tipoEletronico", "ELETRONICO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOEletronico();
        Item item = ItemStubs.createEletronico();
        ItemDTOResponse response = ItemDTOResponseStubs.createItemDTOEletronicoResponse();

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

    @Test
    void TestarCriarItemBebida(){

        ReflectionTestUtils.setField(itemServices, "tipoBebida", "BEBIDA");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOBebida();
        Item item = ItemStubs.createBebida();
        ItemDTOResponse response = ItemDTOResponseStubs.createItemDTOBebidaResponse();

        when(fornecedorRepository.findById(1L))
                .thenReturn(Optional.of(FornecedorStubs.crateFornecedor()));

        when(itemMapper.dtoToEntityBebida(itemDTO))
                .thenReturn((Bebida) item);

        when(itemRepository.save(item))
                .thenReturn(item);

        when(itemMapper.entityToDto(item))
                .thenReturn(response);


        ItemDTOResponse result = itemServices.save("BEBIDA", itemDTO);


        assertNotNull(result);
        assertEquals(response, result);
        assertEquals("Coca Cola", result.getNome());
        verify(itemRepository).save(item);
        verify(fornecedorRepository).findById(1L);

    }

    @Test
    void TestarCriarItemAlimento(){

        ReflectionTestUtils.setField(itemServices, "tipoAlimento", "ALIMENTO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOAlimento();
        Item item = ItemStubs.createAlimento();
        ItemDTOResponse response = ItemDTOResponseStubs.createItemDTOAlimentoResponse();

        when(fornecedorRepository.findById(1L))
                .thenReturn(Optional.of(FornecedorStubs.crateFornecedor()));

        when(itemMapper.dtoToEntityAlimento(itemDTO))
                .thenReturn((Alimento) item);

        when(itemRepository.save(item))
                .thenReturn(item);

        when(itemMapper.entityToDto(item))
                .thenReturn(response);


        ItemDTOResponse result = itemServices.save("ALIMENTO", itemDTO);


        assertNotNull(result);
        assertEquals(response, result);
        assertEquals("Arroz", result.getNome());
        verify(itemRepository).save(item);
        verify(fornecedorRepository).findById(1L);

    }

    @Test
    void TestarCriarItemSemFornecedor(){

        ReflectionTestUtils.setField(itemServices, "tipoAlimento", "ALIMENTO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOAlimento();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemServices.save("ALIMENTO", itemDTO)
        );

        assertTrue(exception.getMessage().contains("Fornecedor não encontrado"));

        verify(itemRepository, never()).save(any());
        verify(itemMapper, never()).dtoToEntityAlimento(any());
    }

    @Test
    void TestarCriarItemComTipoInexistente(){

        ReflectionTestUtils.setField(itemServices, "tipoAlimento", "ALIMENTO");
        ReflectionTestUtils.setField(itemServices, "tipoBebida", "BEBIDA");
        ReflectionTestUtils.setField(itemServices, "tipoEletronico", "ELETRONICO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOAlimento();

        when(fornecedorRepository.findById(1L))
                .thenReturn(Optional.of(FornecedorStubs.crateFornecedor()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemServices.save("CARRO", itemDTO)
        );

        assertTrue(exception.getMessage().contains("Tipo de item inválido"));

        verify(itemRepository, never()).save(any());
        verify(itemMapper, never()).dtoToEntityAlimento(any());
        verify(itemMapper, never()).dtoToEntityBebida(any());
        verify(itemMapper, never()).dtoToEntityEletronico(any());

    }

    @Test
    void TestarErroAoCriarItem(){

        ReflectionTestUtils.setField(itemServices, "tipoAlimento", "ALIMENTO");

        ItemDTO itemDTO = ItemDTOStubs.createItemDTOAlimento();
        Item item = ItemStubs.createAlimento();

        when(fornecedorRepository.findById(1L))
                .thenReturn(Optional.of(FornecedorStubs.crateFornecedor()));

        when(itemMapper.dtoToEntityAlimento(itemDTO))
                .thenThrow(new RuntimeException("Falha na conversão"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> itemServices.save("ALIMENTO", itemDTO)
        );

        assertTrue(exception.getMessage().contains("Erro ao criar o item: " + "Falha na conversão"));
        verify(itemRepository, never()).save(any());

    }

}
