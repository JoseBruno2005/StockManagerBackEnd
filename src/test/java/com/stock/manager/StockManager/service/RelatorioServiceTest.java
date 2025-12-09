package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.dto.response.EstoqueReportDTOResponse;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.mapper.TransacaoMapper;
import com.stock.manager.StockManager.repository.ItemRepository;
import com.stock.manager.StockManager.repository.TransacaoRepository;
import com.stock.manager.StockManager.services.RelatorioService;
import com.stock.manager.StockManager.services.TransacaoService;
import com.stock.manager.StockManager.stubs.itemStubs.ItemStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOResponseStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {
    @InjectMocks
    private RelatorioService relatorioService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    void testarGerarRelatorioMensal() {

        Item item = ItemStubs.createEletronico();

        Transacao venda = TransacaoStubs.createTransacaoVendaStubs();
        Transacao compra = TransacaoStubs.createTransacaoCompraStubs();
        venda.setData(Date.from(LocalDate.of(2025, 1, 10)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        compra.setData(Date.from(LocalDate.of(2025, 1, 15)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(itemRepository.findAll()).thenReturn(List.of(item));
        when(transacaoRepository.findByItemIdAndDataBetween(
                eq(item.getId()), any(Date.class), any(Date.class)
        )).thenReturn(Arrays.asList(venda, compra));

        List<EstoqueReportDTOResponse> relatorio = relatorioService.gerarRelatorioMensal(1, 2025);

        assertNotNull(relatorio);
        assertEquals(1, relatorio.size());

        EstoqueReportDTOResponse dto = relatorio.get(0);

        assertEquals(item.getId(), dto.getItemId());
        assertEquals(item.getNome(), dto.getNomeItem());
        assertEquals(2, dto.getTotalEntradas());
        assertEquals(2, dto.getTotalSaidas());
        assertEquals(item.getQuantidade(), dto.getQuantidadeAtual()); // 10 via stub

        verify(itemRepository).findAll();
        verify(transacaoRepository).findByItemIdAndDataBetween(eq(item.getId()), any(), any());
    }

    @Test
    void deveGerarHistoricoDeTransacoesPorItem() {
        Long itemId = 1L;

        Date inicio = new Date();
        Date fim = new Date();

        Transacao transacaoVenda = TransacaoStubs.createTransacaoVendaStubs();
        Transacao transacaoCompra = TransacaoStubs.createTransacaoCompraStubs();

        List<Transacao> transacoesStub = List.of(transacaoVenda, transacaoCompra);

        Instant fimAjustado = fim.toInstant().plus(1, ChronoUnit.DAYS);
        Date fimCompleto = Date.from(fimAjustado);


        when(transacaoRepository.findByItemIdAndDataBetween(itemId, inicio, fimCompleto))
                .thenReturn(transacoesStub);

        when(transacaoMapper.entityToDto(transacaoVenda))
                .thenReturn(TransacaoDTOResponseStubs.createTransacaoVendaDTOResponseStubs());

        when(transacaoMapper.entityToDto(transacaoCompra))
                .thenReturn(TransacaoDTOResponseStubs.createTransacaoCompraDTOResponseStubs());

        List<TransacaoDTOResponse> resultado =
                relatorioService.gerarHistoricoItem(itemId, inicio, fim);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("venda", resultado.get(0).getTipoTransacao());
        assertEquals("compra", resultado.get(1).getTipoTransacao());

        verify(transacaoRepository).findByItemIdAndDataBetween(itemId, inicio, fimCompleto);
    }
}
