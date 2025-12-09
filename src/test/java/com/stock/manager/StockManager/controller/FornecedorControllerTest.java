package com.stock.manager.StockManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.dto.request.FornecedorDTO;
import com.stock.manager.StockManager.services.FornecedorService;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorDTOStubs;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorStubs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FornecedorController.class)
public class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FornecedorService fornecedorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testarSalvarFornecedor() throws Exception{
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        when(fornecedorService.save(fornecedorDTO)).thenReturn(fornecedorDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/fornecedor/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fornecedorDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(fornecedorDTO.getId()))
                .andExpect(jsonPath("$.nome").value(fornecedorDTO.getNome()));

        verify(fornecedorService).save(fornecedorDTO);
    }

    @Test
    void testarUpdateFornecedor() throws Exception {

        FornecedorDTO updateDTO = FornecedorDTOStubs.updateNomeFornecedorDTO();

        doNothing().when(fornecedorService).updateFornecedor(1L, updateDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/fornecedor/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDTO))
                )
                .andExpect(status().isOk());

        verify(fornecedorService).updateFornecedor(1L, updateDTO);
    }

    @Test
    void testarDeletarFornecedor() throws Exception {

        doNothing().when(fornecedorService).delete(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/fornecedor/1")
                )
                .andExpect(status().isNoContent());

        verify(fornecedorService).delete(1L);
    }

    @Test
    void testarDeletarFornecedorNaoEncontrado() throws Exception {

        doThrow(new IllegalArgumentException("Fornecedor não encontrado"))
                .when(fornecedorService).delete(99L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/fornecedor/99")
                )
                .andExpect(status().isNotFound());

        verify(fornecedorService).delete(99L);
    }

    @Test
    void testarListarTodosFornecedores() throws Exception {

        FornecedorDTO fornecedor1 = FornecedorDTOStubs.createFornecedorDTO();
        FornecedorDTO fornecedor2 = FornecedorDTO.builder()
                .id(2L)
                .nome("Fornecedor 2")
                .telefone("888888888")
                .email("outro@teste.com")
                .CNPJ("11.222.333/0001-11")
                .build();

        when(fornecedorService.listAll()).thenReturn(List.of(fornecedor1, fornecedor2));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/fornecedor/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(fornecedor1.getId()))
                .andExpect(jsonPath("$[0].nome").value(fornecedor1.getNome()))
                .andExpect(jsonPath("$[1].id").value(fornecedor2.getId()))
                .andExpect(jsonPath("$[1].nome").value(fornecedor2.getNome()));

        verify(fornecedorService).listAll();
    }


    @Test
    void testarBuscarFornecedorPorId() throws Exception {

        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        when(fornecedorService.findById(1L)).thenReturn(fornecedorDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/fornecedor/find/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fornecedorDTO.getId()))
                .andExpect(jsonPath("$.nome").value(fornecedorDTO.getNome()));

        verify(fornecedorService).findById(1L);
    }

}
