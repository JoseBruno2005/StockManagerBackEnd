package com.stock.manager.StockManager.repository;

import com.stock.manager.StockManager.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long > {

}
