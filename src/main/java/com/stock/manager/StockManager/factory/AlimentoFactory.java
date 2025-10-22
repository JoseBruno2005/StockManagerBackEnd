package com.stock.manager.StockManager.factory;

import com.stock.manager.StockManager.domain.Alimento;
import com.stock.manager.StockManager.domain.Item;

public class AlimentoFactory implements ItemFactory{
    @Override
    public Item criarItem(Item item) {
        return new Alimento(item.getNome(), item.getPreco(), item.getQuantidade(),
                item.getFoto(), item.getFornecedor());
    }
}
