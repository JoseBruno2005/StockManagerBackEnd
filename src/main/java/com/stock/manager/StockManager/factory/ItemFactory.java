package com.stock.manager.StockManager.factory;

import com.stock.manager.StockManager.domain.Item;

public interface ItemFactory {
    Item criarItem(Item item);
}
