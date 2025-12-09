package com.stock.manager.StockManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.manager.StockManager.dto.response.EstoqueReportDTOResponse;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.services.RelatorioService;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOResponseStubs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RelatorioController.class)
class RelatorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RelatorioService relatorioService;

    @Autowired
    private ObjectMapper objectMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void testarRelatorioMensal() throws Exception {

        EstoqueReportDTOResponse dto = EstoqueReportDTOResponse.builder()
                .itemId(1L)
                .nomeItem("Skol")
                .totalEntradas(5)
                .totalSaidas(2)
                .quantidadeAtual(10)
                .build();

        when(relatorioService.gerarRelatorioMensal(12, 2025))
                .thenReturn(List.of(dto));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/relatorios/mensal")
                                .param("mes", "12")
                                .param("ano", "2025")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemId").value(dto.getItemId()))
                .andExpect(jsonPath("$[0].nomeItem").value(dto.getNomeItem()))
                .andExpect(jsonPath("$[0].totalEntradas").value(dto.getTotalEntradas()))
                .andExpect(jsonPath("$[0].totalSaidas").value(dto.getTotalSaidas()))
                .andExpect(jsonPath("$[0].quantidadeAtual").value(dto.getQuantidadeAtual()));

        verify(relatorioService).gerarRelatorioMensal(12, 2025);
    }

    @Test
    void testarGerarHistoricoItem() throws Exception {

        Long itemId = 1L;
        Date inicio = sdf.parse("2025-01-01");
        Date fim = sdf.parse("2025-01-31");

        TransacaoDTOResponse transacaoDTO = TransacaoDTOResponseStubs.createTransacaoVendaDTOResponseStubs();

        when(relatorioService.gerarHistoricoItem(itemId, inicio, fim))
                .thenReturn(List.of(transacaoDTO));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/relatorios/historico/{id}", itemId)
                                .param("inicio", "2025-01-01")
                                .param("fim", "2025-01-31")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoTransacao").value(transacaoDTO.getTipoTransacao()))
                .andExpect(jsonPath("$[0].quantidade").value(transacaoDTO.getQuantidade()))
                .andExpect(jsonPath("$[0].valor").value(transacaoDTO.getValor()))
                .andExpect(jsonPath("$[0].itemId").value(transacaoDTO.getItemId()));

        verify(relatorioService).gerarHistoricoItem(itemId, inicio, fim);
    }
}
