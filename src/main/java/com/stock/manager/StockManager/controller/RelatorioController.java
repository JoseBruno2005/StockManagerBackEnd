package com.stock.manager.StockManager.controller;

import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.EstoqueReportDTOResponse;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.services.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;


    @GetMapping("/mensal")
    public List<EstoqueReportDTOResponse> getRelatorioMensal(
            @RequestParam int mes,
            @RequestParam int ano
    ) {
        return relatorioService.gerarRelatorioMensal(mes, ano);
    }


    @GetMapping("/historico/{itemId}")
    public ResponseEntity<List<TransacaoDTOResponse>> gerarHistorico(
            @PathVariable Long itemId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim) {

        List<TransacaoDTOResponse> relatorio = relatorioService.gerarHistoricoItem(itemId, inicio, fim);
        return ResponseEntity.ok(relatorio);
    }
}
