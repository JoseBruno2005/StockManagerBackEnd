package com.stock.manager.StockManager.factory;

import com.stock.manager.StockManager.domain.Bebida;
import com.stock.manager.StockManager.domain.Item;

public class BebidaFactory implements ItemFactory{
    @Override
    public Item criarItem(Item item) {
        return new Bebida(item.getNome(), item.getPreco(), item.getQuantidade(),
                item.getFoto(), item.getFornecedor());
    }
}
