package com.stock.manager.StockManager.controller;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.ItemDTO;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.services.ItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServices itemServices;
    @PostMapping("/save")
    public ResponseEntity<ItemDTO> save(@RequestParam String factory, @RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemServices.save(factory,itemDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
            itemServices.updateItem(id, itemDTO);
            return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id){
        try {
            itemServices.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listItens")
    public ResponseEntity<List<ItemDTO>> findAllItems(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemServices.findAllItens());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ItemDTO> findItemById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemServices.findItemById(id));
    }
}
