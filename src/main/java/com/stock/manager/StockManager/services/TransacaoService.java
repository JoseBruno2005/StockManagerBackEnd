package com.stock.manager.StockManager.services;

import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.domain.Transacao;
import com.stock.manager.StockManager.dto.ItemDTO;
import com.stock.manager.StockManager.dto.TransacaoDTO;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.mapper.TransacaoMapper;
import com.stock.manager.StockManager.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final ItemServices itemServices;
    private final ItemMapper itemMapper;

    public TransacaoDTO criarTransacao(TransacaoDTO transacaoDTO){

        String tipo = transacaoDTO.getTipoTransacao().toUpperCase();
        Transacao transacao = transacaoMapper.dtoToEntity(transacaoDTO);

        Item item = itemServices.findItemEntityById(transacaoDTO.getItemId());

        transacao.setItem(item);

        transacao.setValor(item.getPreco() * transacao.getQuantidade());

        switch (tipo){
            case "VENDA":
                if (transacao.getQuantidade() <= 0) {
                    throw new IllegalArgumentException("A quantidade de venda deve ser maior que zero.");
                } else if (transacao.getQuantidade() > item.getQuantidade()) {
                    throw new IllegalArgumentException("Quantidade em estoque insuficiente para realizar a venda.");
                } else {
                    item.setQuantidade(item.getQuantidade() - transacao.getQuantidade());
                    itemServices.updateItem(item.getId(), itemMapper.entityToDto(item));
                    transacaoRepository.save(transacao);
                }
                break;
            case "COMPRA":
                if(transacao.getQuantidade() <= 0 ){
                    throw new IllegalArgumentException("A quantidade de compra deve ser maior que zero.");
                }else {
                    item.setQuantidade(item.getQuantidade() + transacao.getQuantidade());
                    itemServices.updateItem(item.getId(), itemMapper.entityToDto(item));
                    transacaoRepository.save(transacao);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipo);
        }
        return transacaoMapper.entityToDto(transacao);
    }

}
