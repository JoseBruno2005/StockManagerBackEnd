package com.stock.manager.StockManager.repository;

import com.stock.manager.StockManager.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
