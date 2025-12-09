package com.stock.manager.StockManager.service;

import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.dto.request.FornecedorDTO;
import com.stock.manager.StockManager.mapper.FornecedorMapper;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import com.stock.manager.StockManager.services.FornecedorService;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorDTOStubs;
import com.stock.manager.StockManager.stubs.fornecedorStubs.FornecedorStubs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FornecedorServiceTest {

    @InjectMocks
    private FornecedorService fornecedorService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FornecedorMapper fornecedorMapper;

    @Test
    void testarCriarFornecedor(){
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();

        when(fornecedorMapper.dtoToEntity(fornecedorDTO)).thenReturn(fornecedor);

        when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);

        when(fornecedorMapper.entityToDto(fornecedor)).thenReturn(fornecedorDTO);

        FornecedorDTO result = fornecedorService.save(fornecedorDTO);

        assertNotNull(result);
        assertEquals(fornecedorDTO, result);
        assertEquals("Fornecedor Teste", fornecedorDTO.getNome());
        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void testarCriarFornecedorErroIllegalArgumentException() {
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        when(fornecedorMapper.dtoToEntity(fornecedorDTO))
                .thenThrow(new IllegalArgumentException("Erro no mapper"));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    fornecedorService.save(fornecedorDTO);
                });

        assertEquals("Erro no mapper", exception.getMessage());
    }

    @Test
    void testarCriarFornecedorErroRuntimeException() {
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        when(fornecedorMapper.dtoToEntity(fornecedorDTO))
                .thenThrow(new RuntimeException("Falha inesperada"));

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {
                    fornecedorService.save(fornecedorDTO);
                });

        assertTrue(exception.getMessage().contains("Erro ao criar o item: " + "Falha inesperada"));
    }

    @Test
    void testarAtualizarFornecedorNome(){
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateNomeFornecedorDTO();

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor));

        fornecedorService.updateFornecedor(id, fornecedorDTO);

        assertEquals("Fornecedor Atualizado", fornecedor.getNome());

        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void testarAtualizarFornecedorTelefone() {
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateTelefoneFornecedorDTO();

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor));

        fornecedorService.updateFornecedor(id, fornecedorDTO);

        assertEquals("111111111", fornecedor.getTelefone());
        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void testarAtualizarFornecedorEmail() {
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateEmailFornecedorDTO();

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor));

        fornecedorService.updateFornecedor(id, fornecedorDTO);

        assertEquals("email@atualizado.com", fornecedor.getEmail());
        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void testarAtualizarFornecedorCNPJ() {
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateCNPJFornecedorDTO();

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor));

        fornecedorService.updateFornecedor(id, fornecedorDTO);

        assertEquals("11.111.111/0001-11", fornecedor.getCNPJ());
        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void testarAtualizarFornecedorNaoEncontrado() {
        Long id = 1L;
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateNomeFornecedorDTO();

        when(fornecedorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            fornecedorService.updateFornecedor(id, fornecedorDTO);
        });
    }

    @Test
    void testarAtualizarFornecedorRuntimeException() {
        Long id = 1L;
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.updateNomeFornecedorDTO();

        when(fornecedorRepository.findById(id))
                .thenThrow(new RuntimeException("Falha inesperada"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fornecedorService.updateFornecedor(id, fornecedorDTO);
        });

        assertTrue(exception.getMessage().contains("Erro ao atualizar o fornecedor"));
    }

    @Test
    void testarBuscarTodosOsFornecedores(){
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        List<Fornecedor> lista = new ArrayList<>();
        lista.add(fornecedor);

        when(fornecedorRepository.findAll()).thenReturn(lista);
        when(fornecedorMapper.entityToDto(fornecedor)).thenReturn(fornecedorDTO);

        List<FornecedorDTO> result = fornecedorService.listAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fornecedor Teste", result.get(0).getNome());
        assertEquals(fornecedorDTO, result.get(0));

        verify(fornecedorRepository).findAll();
        verify(fornecedorMapper).entityToDto(fornecedor);
    }

    @Test
    void testarBuscarTodosOsFornecedoresErroRuntimeException(){

        when(fornecedorRepository.findAll())
                .thenThrow(new RuntimeException("Lista Vazia"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fornecedorService.listAll();
        });

        assertTrue(exception.getMessage().contains("Erro ao buscar fornecedores: " + "Lista Vazia"));
    }

    @Test
    void testarBuscarFornecedorPorId(){
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();
        FornecedorDTO fornecedorDTO = FornecedorDTOStubs.createFornecedorDTO();

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor));
        when(fornecedorMapper.entityToDto(fornecedor)).thenReturn(fornecedorDTO);

        FornecedorDTO result = fornecedorService.findById(id);

        assertNotNull(result);
        assertEquals("Fornecedor Teste", result.getNome());
        assertEquals(fornecedorDTO, result);

        verify(fornecedorRepository).findById(id);
        verify(fornecedorMapper).entityToDto(fornecedor);
    }

    @Test
    void testarBuscarFornecedorePorIdErroRuntimeException(){
        Long id = 1L;

        when(fornecedorRepository.findById(id))
                .thenThrow(new RuntimeException("Não Encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fornecedorService.findById(id);
        });

        assertTrue(exception.getMessage()
                .contains("Erro ao buscar fornecedor com ID " + id + "Não Encontrado"));
    }

    @Test
    void testarDeletarFornecedor(){
        Long id = 1L;
        Fornecedor fornecedor = FornecedorStubs.crateFornecedor();

        when(fornecedorRepository.findById(id))
                .thenReturn(Optional.of(fornecedor));

        fornecedorService.delete(id);

        verify(fornecedorRepository).findById(id);
        verify(fornecedorRepository).deleteById(id);
    }

    @Test
    void testarDeletarFornecedoreErroRuntimeException(){
        Long id = 1L;

        when(fornecedorRepository.findById(id))
                .thenThrow(new RuntimeException("Não Encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fornecedorService.delete(id);
        });

        assertTrue(exception.getMessage()
                .contains("Erro ao excluir o fornecedor com ID " + id + "Não Encontrado"));
    }
}
