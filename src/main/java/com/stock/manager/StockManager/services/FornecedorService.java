package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public Fornecedor criar(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }
    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado: " + id));
    }
}
