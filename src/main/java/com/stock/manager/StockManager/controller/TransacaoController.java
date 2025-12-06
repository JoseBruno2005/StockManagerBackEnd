package com.stock.manager.StockManager.controller;

import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping("/save")
    public ResponseEntity<TransacaoDTOResponse> save(@RequestBody TransacaoDTO transacaoDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transacaoService.criarTransacao(transacaoDTO));
    }

}
