package com.stock.manager.StockManager.services;
import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.ItemDTO;
import com.stock.manager.StockManager.factory.AlimentoFactory;
import com.stock.manager.StockManager.factory.BebidaFactory;
import com.stock.manager.StockManager.factory.EletronicoFactory;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import com.stock.manager.StockManager.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServices {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final FornecedorRepository fornecedorRepository;

    public ItemDTO criarItem(String factory, ItemDTO itemDTO){

        //Adicionar Try Cacth em todos os serviços

        Fornecedor fornecedor = fornecedorRepository.findById(itemDTO.getFornecedorId())
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado" +
                        itemDTO.getFornecedorId()));

        Item itemBase;
        Item item;

        switch (factory.toUpperCase()){
            case "ALIMENTO" :
                itemBase = itemMapper.dtoToEntityAlimento(itemDTO);
                item = new AlimentoFactory().criarItem(itemBase);
                break;
            case "BEBIDA" :
                itemBase = itemMapper.dtoToEntityBebida(itemDTO);
                item = new BebidaFactory().criarItem(itemBase);
                break;
            case "ELETRONICO" :
                itemBase = itemMapper.dtoToEntityEletronico(itemDTO);
                item = new EletronicoFactory().criarItem(itemBase);
                break;
            default:
                throw new IllegalArgumentException("Tipo de item inválido: " + factory);
        }
        item.setFornecedor(fornecedor);
        item = itemRepository.save(item);
        return itemMapper.entityToDto(item);
    }

    @Transactional
    public void updateItem(Long id, ItemDTO itemDTO){
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + id + " não encontrado."));

        if(itemDTO.getNome() != null && !itemDTO.getNome().trim().isEmpty()){
            existingItem.setNome(itemDTO.getNome());
        }

        if(itemDTO.getPreco() != null && itemDTO.getPreco() > 0){
            existingItem.setPreco(itemDTO.getPreco());
        }

        if(itemDTO.getQuantidade() != null && itemDTO.getQuantidade() >= 0){
            existingItem.setQuantidade(itemDTO.getQuantidade());
        }

        if(itemDTO.getFoto() != null && !itemDTO.getNome().trim().isEmpty()){
            existingItem.setFoto(itemDTO.getFoto());
        }

        itemRepository.save(existingItem);
    }

    public void deleteItem(Long id){
        try{
            Item existingItem = itemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + id + " não encontrado."));

            itemRepository.deleteById(id);
        }catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir o item com ID " + id + ".", e);
        }
    }

    public List<ItemDTO> findAllItens(){
        List<Item> listItem = itemRepository.findAll();

        List<ItemDTO> listItemDto = new ArrayList<>();

        listItem.forEach(item -> {
            listItemDto.add(itemMapper.entityToDto(item));
        });

        return listItemDto;
    }

    public ItemDTO findItemById(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item com ID " + id + " não encontrado."));

        return itemMapper.entityToDto(item);
    }

}
