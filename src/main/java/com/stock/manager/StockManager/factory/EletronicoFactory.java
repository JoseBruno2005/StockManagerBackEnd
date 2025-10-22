package com.stock.manager.StockManager.factory;

import com.stock.manager.StockManager.domain.Eletronico;
import com.stock.manager.StockManager.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class EletronicoFactory implements ItemFactory{
    @Override
    public Item criarItem(Item item) {
        return new Eletronico(item.getNome(), item.getPreco(), item.getQuantidade(),
                item.getFoto(), item.getFornecedor());
    }
}
