package com.stock.manager.StockManager.controller;

import com.stock.manager.StockManager.dto.EstoqueReportDTO;
import com.stock.manager.StockManager.dto.TransacaoDTO;
import com.stock.manager.StockManager.dto.TransacaoHistoricoDTO;
import com.stock.manager.StockManager.services.RelatorioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RelatorioController {

    private final RelatorioService relatorioService;


    @GetMapping("/mensal")
    public List<EstoqueReportDTO> getRelatorioMensal(
            @RequestParam int mes,
            @RequestParam int ano
    ) {
        return relatorioService.gerarRelatorioMensal(mes, ano);
    }


    @GetMapping("/historico/{itemId}")
    public ResponseEntity<List<TransacaoDTO>> gerarHistorico(
            @PathVariable Long itemId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim) {

        List<TransacaoDTO> relatorio = relatorioService.gerarHistoricoItem(itemId, inicio, fim);
        return ResponseEntity.ok(relatorio);
    }
}
