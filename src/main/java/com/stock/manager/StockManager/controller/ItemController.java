package com.stock.manager.StockManager.controller;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
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
    public ResponseEntity<ItemDTOResponse> save(@RequestParam String factory, @RequestBody ItemDTO itemDTO){
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
    public ResponseEntity<List<ItemDTOResponse>> findAllItems(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemServices.findAllItens());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ItemDTOResponse> findItemById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemServices.findItemById(id));
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<String> getFoto(@PathVariable Long id) {
        String foto = itemServices.getFoto(id);
        return ResponseEntity.ok(foto);
    }
}
