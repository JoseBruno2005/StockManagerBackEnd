package com.stock.manager.StockManager.controller;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.ItemDTO;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.services.ItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServices itemServices;
    private final ItemMapper itemMapper;

    @PostMapping("/criar")
    public ResponseEntity<ItemDTO> save(@RequestParam String factory, @RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemServices.criarItem(factory,itemDTO));
    }
}
