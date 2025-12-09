package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.mapper.TransacaoMapper;
import com.stock.manager.StockManager.repository.ItemRepository;
import com.stock.manager.StockManager.repository.TransacaoRepository;
import com.stock.manager.StockManager.services.ItemServices;
import com.stock.manager.StockManager.services.TransacaoService;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOResponseStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOResponseStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private ItemServices itemServices;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    void testarSalvarTransacaoVenda(){

        ReflectionTestUtils.setField(transacaoService, "venda", "VENDA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoVendaDTOStubs();
        Transacao transacao = TransacaoStubs.createTransacaoVendaStubs();

        TransacaoDTOResponse transacaoDTOResponse = TransacaoDTOResponseStubs
                .createTransacaoVendaDTOResponseStubs();

        Item item = ItemStubs.createEletronico();
        ItemDTOResponse itemDTOResponse = ItemDTOResponseStubs.createItemDTOEletronicoResponse();
        item.setQuantidade(10);

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);
        when(itemMapper.entityToDto(item)).thenReturn(itemDTOResponse);
        when(transacaoRepository.save(transacao)).thenReturn(transacao);
        when(transacaoMapper.entityToDto(transacao)).thenReturn(transacaoDTOResponse);

        TransacaoDTOResponse response = transacaoService.criarTransacao(transacaoDTO);

        assertNotNull(response);
        assertEquals(transacaoDTOResponse.getQuantidade(), response.getQuantidade());
        assertEquals(transacaoDTOResponse.getTipoTransacao(), response.getTipoTransacao());

        assertEquals(8, item.getQuantidade());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices).updateQuantidade(eq(1L), any(ItemDTOResponse.class));
        verify(transacaoRepository).save(transacao);
        verify(transacaoMapper).entityToDto(transacao);

    }

    @Test
    void testarSalvarTransacaoVendaComQuantidadeMenosQueZero(){

        ReflectionTestUtils.setField(transacaoService, "venda", "VENDA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoVendaDTOStubs();
        Transacao transacao = TransacaoStubs.createTransacaoVendaStubs();

        TransacaoDTOResponse transacaoDTOResponse = TransacaoDTOResponseStubs
                .createTransacaoVendaDTOResponseStubs();

        Item item = ItemStubs.createEletronico();
        ItemDTOResponse itemDTOResponse = ItemDTOResponseStubs.createItemDTOEletronicoResponse();
        item.setQuantidade(-1);

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.criarTransacao(transacaoDTO);
        });;

        assertNotNull(exception);
        assertEquals("Quantidade em estoque insuficiente para realizar a venda.",
                exception.getMessage());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices, never()).updateQuantidade(eq(1L), any(ItemDTOResponse.class));
        verify(transacaoRepository, never()).save(transacao);
        verify(transacaoMapper, never()).entityToDto(transacao);

    }

    @Test
    void testarSalvarTransacaoVendaComQuantidadeIgualZero() {

        ReflectionTestUtils.setField(transacaoService, "venda", "VENDA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoVendaDTOStubs();
        Transacao transacao = TransacaoStubs.createTransacaoVendaStubs();
        transacao.setQuantidade(0);

        Item item = ItemStubs.createEletronico();
        item.setQuantidade(10);

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacaoDTO)
        );

        assertEquals("A quantidade de venda deve ser maior que zero.", exception.getMessage());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices, never()).updateQuantidade(anyLong(), any());
        verify(transacaoRepository, never()).save(any());
        verify(transacaoMapper, never()).entityToDto(any());
    }

    @Test
    void testarSalvarTransacaoCompra(){

        ReflectionTestUtils.setField(transacaoService, "compra", "COMPRA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoCompraDTOStubs();
        Transacao transacao = TransacaoStubs.createTransacaoCompraStubs();

        TransacaoDTOResponse transacaoDTOResponse = TransacaoDTOResponseStubs
                .createTransacaoCompraDTOResponseStubs();

        Item item = ItemStubs.createEletronico();
        ItemDTOResponse itemDTOResponse = ItemDTOResponseStubs.createItemDTOEletronicoResponse();
        item.setQuantidade(10);

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);
        when(itemMapper.entityToDto(item)).thenReturn(itemDTOResponse);
        when(transacaoRepository.save(transacao)).thenReturn(transacao);
        when(transacaoMapper.entityToDto(transacao)).thenReturn(transacaoDTOResponse);

        TransacaoDTOResponse response = transacaoService.criarTransacao(transacaoDTO);

        assertNotNull(response);
        assertEquals(transacaoDTOResponse.getQuantidade(), response.getQuantidade());
        assertEquals(transacaoDTOResponse.getTipoTransacao(), response.getTipoTransacao());

        assertEquals(12, item.getQuantidade());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices).updateQuantidade(eq(1L), any(ItemDTOResponse.class));
        verify(transacaoRepository).save(transacao);
        verify(transacaoMapper).entityToDto(transacao);

    }

    @Test
    void testarSalvarTransacaoCompraComQuantidadeInvalida() {

        ReflectionTestUtils.setField(transacaoService, "compra", "COMPRA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoCompraDTOStubs();
        Transacao transacao = TransacaoStubs.createTransacaoCompraStubs();
        transacao.setQuantidade(0);

        Item item = ItemStubs.createEletronico();
        item.setQuantidade(5);

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.criarTransacao(transacaoDTO)
        );

        assertEquals("A quantidade de compra deve ser maior que zero.", exception.getMessage());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices, never()).updateQuantidade(anyLong(), any());
        verify(transacaoRepository, never()).save(any());
        verify(transacaoMapper, never()).entityToDto(any());
    }

    @Test
    void testarSalvarTransacaoComTipoInvalido() {

        ReflectionTestUtils.setField(transacaoService, "venda", "VENDA");
        ReflectionTestUtils.setField(transacaoService, "compra", "COMPRA");

        TransacaoDTO transacaoDTO = TransacaoDTOStubs.createTransacaoCompraDTOStubs();
        transacaoDTO.setTipoTransacao("INVALIDO");

        Transacao transacao = TransacaoStubs.createTransacaoCompraStubs();

        Item item = ItemStubs.createEletronico();

        when(transacaoMapper.dtoToEntity(transacaoDTO)).thenReturn(transacao);
        when(itemServices.findItemEntityById(transacaoDTO.getItemId())).thenReturn(item);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transacaoService.criarTransacao(transacaoDTO)
        );

        assertEquals("Tipo de transação inesperado: INVALIDO", exception.getMessage());

        verify(transacaoMapper).dtoToEntity(transacaoDTO);
        verify(itemServices).findItemEntityById(transacaoDTO.getItemId());
        verify(itemServices, never()).updateQuantidade(anyLong(), any());
        verify(transacaoRepository, never()).save(any());
        verify(transacaoMapper, never()).entityToDto(any());
    }

}
