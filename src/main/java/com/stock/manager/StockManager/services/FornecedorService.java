package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.dto.request.FornecedorDTO;
import com.stock.manager.StockManager.mapper.FornecedorMapper;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        try{
            Fornecedor fornecedor =  fornecedorMapper.dtoToEntity(fornecedorDTO);
            return fornecedorMapper.entityToDto(fornecedorRepository.save(fornecedor));
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e) {
            throw new RuntimeException("Erro ao criar o item: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void updateFornecedor(Long id, FornecedorDTO fornecedorDTO){
        try {
            Fornecedor existingForneceor = fornecedorRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + id + " não encontrado."));

            if (fornecedorDTO.getNome() != null && !fornecedorDTO.getNome().trim().isEmpty()) {
                existingForneceor.setNome(fornecedorDTO.getNome());
            }

            if (fornecedorDTO.getTelefone() != null && !fornecedorDTO.getTelefone().trim().isEmpty()) {
                existingForneceor.setTelefone(fornecedorDTO.getTelefone());
            }

            if (fornecedorDTO.getEmail() != null && !fornecedorDTO.getEmail().trim().isEmpty()) {
                existingForneceor.setEmail(fornecedorDTO.getEmail());
            }

            if (fornecedorDTO.getCNPJ() != null && !fornecedorDTO.getCNPJ().trim().isEmpty()) {
                existingForneceor.setCNPJ(fornecedorDTO.getCNPJ());
            }

            fornecedorRepository.save(existingForneceor);
        }catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o fornecedor com ID " + id + ".", e);
        }
    }
    public List<FornecedorDTO> listAll() {
        try{
            List<Fornecedor> listFornecedor = fornecedorRepository.findAll();

            List<FornecedorDTO> listFornecedorDTO = new ArrayList<>();

            listFornecedor.forEach(fornecedor -> {
                listFornecedorDTO.add(fornecedorMapper.entityToDto(fornecedor));
            });
            return listFornecedorDTO;
        }catch (Exception e) {
            throw new RuntimeException("Erro ao buscar itens: " + e.getMessage(), e);
        }
    }
    public FornecedorDTO findById(Long id) {
        try{
            Fornecedor fornecedor = fornecedorRepository.findById(id).
                    orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado."));

            return fornecedorMapper.entityToDto(fornecedor);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao buscar item com ID " + id + ".", e);
        }
    }

    public void delete(Long id){
        try{
            Fornecedor existingFornecedor = fornecedorRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado: " + id));

             fornecedorRepository.deleteById(id);
        }catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir o item com ID " + id + ".", e);
        }
    }
}
