package com.stock.manager.StockManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
import com.stock.manager.StockManager.services.ItemServices;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOResponseStubs;
import com.stock.manager.StockManager.stubs.itemStubs.ItemDTOStubs;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemServices itemServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testarSalvarFornecedor() throws Exception {
        ItemDTO itemDTOEletronico = ItemDTOStubs.createItemDTOEletronico();
        ItemDTOResponse itemDTOResponse = ItemDTOResponseStubs.createItemDTOEletronicoResponse();

        when(itemServices.save("Eletronico", itemDTOEletronico)).thenReturn(itemDTOResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/item/save")
                                .param("factory", "Eletronico")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(itemDTOEletronico))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(itemDTOResponse.getId()))
                .andExpect(jsonPath("$.nome").value(itemDTOEletronico.getNome()));

        verify(itemServices).save("Eletronico", itemDTOEletronico);
    }

    @Test
    void testarUpdateItem() throws Exception {

        ItemDTO updateDTO = ItemDTOStubs.createItemDTOEletronico();

        doNothing().when(itemServices).updateItem(1L, updateDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/item/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDTO))
                )
                .andExpect(status().isOk());

        verify(itemServices).updateItem(1L, updateDTO);

    }

    @Test
    void testarDeletarItem() throws Exception {

        doNothing().when(itemServices).deleteItem(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/item/1")
                )
                .andExpect(status().isNoContent());

        verify(itemServices).deleteItem(1L);
    }

    @Test
    void testarDeletarItemNaoEncontrado() throws Exception {

        doThrow(new IllegalArgumentException("Item não encontrado"))
                .when(itemServices).deleteItem(99L);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/item/99")
                )
                .andExpect(status().isNotFound());

        verify(itemServices).deleteItem(99L);
    }

    @Test
    void testarListarTodosItens() throws Exception {

        ItemDTOResponse item1 = ItemDTOResponseStubs.createItemDTOEletronicoResponse();
        ItemDTOResponse item2 = ItemDTOResponse.builder()
                .id(2L)
                .nome("Cadeira Gamer")
                .quantidade(5)
                .preco(499.90)
                .build();

        when(itemServices.findAllItens()).thenReturn(List.of(item1, item2));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/item/listItens")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item1.getId()))
                .andExpect(jsonPath("$[0].nome").value(item1.getNome()))
                .andExpect(jsonPath("$[1].id").value(item2.getId()))
                .andExpect(jsonPath("$[1].nome").value(item2.getNome()));

        verify(itemServices).findAllItens();
    }

    @Test
    void testarBuscarItemPorId() throws Exception {

        ItemDTOResponse response = ItemDTOResponseStubs.createItemDTOEletronicoResponse();

        when(itemServices.findItemById(1L)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/item/find/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getNome()));

        verify(itemServices).findItemById(1L);
    }

    @Test
    void testarGetFoto() throws Exception {

        when(itemServices.getFoto(1L)).thenReturn("base64FotoTeste");

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/item/foto/1")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("base64FotoTeste"));

        verify(itemServices).getFoto(1L);
    }
}