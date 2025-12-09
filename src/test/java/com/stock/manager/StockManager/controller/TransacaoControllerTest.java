package com.stock.manager.StockManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.services.TransacaoService;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOResponseStubs;
import com.stock.manager.StockManager.stubs.transacaoStubs.TransacaoDTOStubs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransacaoController.class)
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService transacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testarCriarTransacaoVenda() throws Exception {

        TransacaoDTO dtoRequest = TransacaoDTOStubs.createTransacaoVendaDTOStubs();
        TransacaoDTOResponse dtoResponse = TransacaoDTOResponseStubs.createTransacaoVendaDTOResponseStubs();

        when(transacaoService.criarTransacao(dtoRequest)).thenReturn(dtoResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/transacao/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoTransacao").value(dtoResponse.getTipoTransacao()))
                .andExpect(jsonPath("$.quantidade").value(dtoResponse.getQuantidade()))
                .andExpect(jsonPath("$.valor").value(dtoResponse.getValor()))
                .andExpect(jsonPath("$.itemId").value(dtoResponse.getItemId()));

        verify(transacaoService).criarTransacao(dtoRequest);
    }
}
