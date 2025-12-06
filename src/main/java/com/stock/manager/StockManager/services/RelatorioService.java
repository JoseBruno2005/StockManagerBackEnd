package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.dto.request.TransacaoDTO;
import com.stock.manager.StockManager.dto.response.EstoqueReportDTOResponse;
import com.stock.manager.StockManager.dto.response.TransacaoDTOResponse;
import com.stock.manager.StockManager.mapper.TransacaoMapper;
import com.stock.manager.StockManager.repository.ItemRepository;
import com.stock.manager.StockManager.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final TransacaoRepository transacaoRepository;
    private final ItemRepository itemRepository;
    private final TransacaoMapper transacaoMapper;

    public List<EstoqueReportDTOResponse> gerarRelatorioMensal(int mes, int ano) {

        List<EstoqueReportDTOResponse> relatorio = new ArrayList<>();
        List<Item> itens = itemRepository.findAll();

        for (Item item : itens) {

            LocalDate inicioMes = LocalDate.of(ano, mes, 1);
            LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            Date inicio = Date.from(inicioMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fim = Date.from(fimMes.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<Transacao> transacoes = transacaoRepository.findByItemIdAndDataBetween(item.getId(), inicio, fim);

            int totalEntradas = transacoes.stream()
                    .filter(t -> t.getTipoTransacao().equalsIgnoreCase("compra"))
                    .mapToInt(Transacao::getQuantidade)
                    .sum();

            int totalSaidas = transacoes.stream()
                    .filter(t -> t.getTipoTransacao().equalsIgnoreCase("venda"))
                    .mapToInt(Transacao::getQuantidade)
                    .sum();

            int quantidadeAtual = item.getQuantidade();

            EstoqueReportDTOResponse dto = EstoqueReportDTOResponse.builder()
                    .itemId(item.getId())
                    .nomeItem(item.getNome())
                    .totalEntradas(totalEntradas)
                    .totalSaidas(totalSaidas)
                    .quantidadeAtual(quantidadeAtual)
                    .build();

            relatorio.add(dto);
        }

        return relatorio;
    }

    @Transactional(readOnly = true)
    public List<TransacaoDTOResponse> gerarHistoricoItem(Long itemId, Date inicio, Date fim) {
        Instant fimAjustado = fim.toInstant().plus(1, ChronoUnit.DAYS);
        Date fimCompleto = Date.from(fimAjustado);

        List<Transacao> transacoes = transacaoRepository.findByItemIdAndDataBetween(itemId, inicio, fimCompleto);

        return transacoes.stream()
                .map(transacaoMapper::entityToDto)
                .toList();
    }
}
