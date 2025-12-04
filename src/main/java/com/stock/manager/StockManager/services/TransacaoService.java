package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.dto.TransacaoDTO;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.mapper.TransacaoMapper;
import com.stock.manager.StockManager.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final ItemServices itemServices;
    private final ItemMapper itemMapper;

    @Value("${transacao.type.venda}")
    private String venda;

    @Value("${transacao.type.compra}")
    private String compra;

    public TransacaoDTO criarTransacao(TransacaoDTO transacaoDTO){

        String tipo = transacaoDTO.getTipoTransacao().toUpperCase();
        Transacao transacao = transacaoMapper.dtoToEntity(transacaoDTO);

        Item item = itemServices.findItemEntityById(transacaoDTO.getItemId());
        transacao.setItem(item);
        transacao.setValor(item.getPreco() * transacao.getQuantidade());

        if (tipo.equalsIgnoreCase(venda)) {
            if (transacao.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de venda deve ser maior que zero.");
            } else if (transacao.getQuantidade() > item.getQuantidade()) {
                throw new IllegalArgumentException("Quantidade em estoque insuficiente para realizar a venda.");
            } else {
                item.setQuantidade(item.getQuantidade() - transacao.getQuantidade());
                itemServices.updateQuantidade(item.getId(), itemMapper.entityToDto(item));
                transacaoRepository.save(transacao);
            }

        } else if (tipo.equalsIgnoreCase(compra)) {
            if (transacao.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de compra deve ser maior que zero.");
            } else {
                item.setQuantidade(item.getQuantidade() + transacao.getQuantidade());
                itemServices.updateQuantidade(item.getId(), itemMapper.entityToDto(item));
                transacaoRepository.save(transacao);
            }

        } else {
            throw new IllegalStateException("Tipo de transação inesperado: " + tipo);
        }

        return transacaoMapper.entityToDto(transacao);
    }

}
