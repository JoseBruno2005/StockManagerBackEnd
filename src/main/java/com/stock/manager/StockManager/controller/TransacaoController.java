package com.stock.manager.StockManager.controller;

import com.stock.manager.StockManager.dto.TransacaoDTO;
import com.stock.manager.StockManager.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping("/save")
    public ResponseEntity<TransacaoDTO> save(@RequestBody TransacaoDTO transacaoDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transacaoService.criarTransacao(transacaoDTO));
    }

}
