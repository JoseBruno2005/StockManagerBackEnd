package com.stock.manager.StockManager.repository;

import com.stock.manager.StockManager.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query("SELECT t FROM Transacao t WHERE t.item.id = :itemId AND t.data BETWEEN :inicio AND :fim")
    List<Transacao> findByItemIdAndDataBetween(@Param("itemId") Long itemId,
                                               @Param("inicio") Date inicio,
                                               @Param("fim") Date fim);

}
