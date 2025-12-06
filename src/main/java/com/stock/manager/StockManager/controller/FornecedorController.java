package com.stock.manager.StockManager.controller;

import com.stock.manager.StockManager.dto.request.FornecedorDTO;
import com.stock.manager.StockManager.services.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
@RequiredArgsConstructor
public class FornecedorController {
    private final FornecedorService fornecedorService;

    @PostMapping("/save")
    public ResponseEntity<FornecedorDTO> save(@RequestBody FornecedorDTO fornecedorDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                fornecedorService.save(fornecedorDTO)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> udpate(@PathVariable("id") Long id, @RequestBody
    FornecedorDTO fornecedorDTO){
        fornecedorService.updateFornecedor(id, fornecedorDTO);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        try {
            fornecedorService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/list")
    public ResponseEntity<List<FornecedorDTO>> listAllFornecedores(){
        return ResponseEntity.status(HttpStatus.OK).body(
                fornecedorService.listAll());
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<FornecedorDTO> findFornecedorById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).
                body(fornecedorService.findById(id));
    }
}
