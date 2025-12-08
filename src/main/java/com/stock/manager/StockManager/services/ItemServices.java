package com.stock.manager.StockManager.services;
import com.stock.manager.StockManager.domain.Fornecedor;
import com.stock.manager.StockManager.domain.Item;
import com.stock.manager.StockManager.dto.request.ItemDTO;
import com.stock.manager.StockManager.dto.response.ItemDTOResponse;
import com.stock.manager.StockManager.factory.AlimentoFactory;
import com.stock.manager.StockManager.factory.BebidaFactory;
import com.stock.manager.StockManager.factory.EletronicoFactory;
import com.stock.manager.StockManager.mapper.ItemMapper;
import com.stock.manager.StockManager.repository.FornecedorRepository;
import com.stock.manager.StockManager.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServices {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final FornecedorRepository fornecedorRepository;

    @Value("${item.type.alimento}")
    private String tipoAlimento;

    @Value("${item.type.bebida}")
    private String tipoBebida;

    @Value("${item.type.eletronico}")
    private String tipoEletronico;

    public ItemDTOResponse save(String factory, ItemDTO itemDTO) {

        try {
            Fornecedor fornecedor = fornecedorRepository.findById(itemDTO.getFornecedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

            Item itemBase;
            Item item;

            if (factory.equalsIgnoreCase(tipoAlimento)) {
                itemBase = itemMapper.dtoToEntityAlimento(itemDTO);
                item = new AlimentoFactory().criarItem(itemBase);
            } else if (factory.equalsIgnoreCase(tipoBebida)) {
                itemBase = itemMapper.dtoToEntityBebida(itemDTO);
                item = new BebidaFactory().criarItem(itemBase);
            } else if (factory.equalsIgnoreCase(tipoEletronico)) {
                itemBase = itemMapper.dtoToEntityEletronico(itemDTO);
                item = new EletronicoFactory().criarItem(itemBase);
            } else {
                throw new IllegalArgumentException("Tipo de item inválido");
            }

            item.setFornecedor(fornecedor);
            item.setQuantidade(0);   //aqui
            item = itemRepository.save(item);
            return itemMapper.entityToDto(item);
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e) {
            throw new RuntimeException("Erro ao criar o item: " + e.getMessage());
        }
    }

    @Transactional
    public void updateItem(Long id, ItemDTO itemDTO){
        try {
            Item existingItem = itemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Item com ID " + id + " não encontrado."));

            if (itemDTO.getNome() != null && !itemDTO.getNome().trim().isEmpty()) {
                existingItem.setNome(itemDTO.getNome());
            }

            if (itemDTO.getPreco() != null && itemDTO.getPreco() > 0) {
                existingItem.setPreco(itemDTO.getPreco());
            }

            if (itemDTO.getQuantidade() != null) {
                throw new IllegalArgumentException("Erro! a quantidade do item não pode ser modificada");
            }

            if (itemDTO.getFoto() != null && !itemDTO.getNome().trim().isEmpty()) {
                existingItem.setFoto(itemDTO.getFoto());
            }

            itemRepository.save(existingItem);
        }catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o item");
        }
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

    public List<ItemDTOResponse> findAllItens(){
        try{
            List<Item> listItem = itemRepository.findAll();

            List<ItemDTOResponse> listItemDto = new ArrayList<>();

            listItem.forEach(item -> {
                listItemDto.add(itemMapper.entityToDto(item));
            });

            return listItemDto;
        }catch (Exception e) {
            throw new RuntimeException("Erro ao buscar itens: " + e.getMessage(), e);
        }
    }

    public ItemDTOResponse findItemById(Long id){
        try{
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Item com ID " + id + " não encontrado."));

            return itemMapper.entityToDto(item);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao buscar item com ID " + id + ".", e);
        }
    }

    public Item findItemEntityById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
    }

    public void updateQuantidade(Long id, ItemDTOResponse itemDTOResponse){
        try{
            Item existingItem = itemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Item com ID " + id + " não encontrado."));

            existingItem.setQuantidade(itemDTOResponse.getQuantidade());
            itemRepository.save(existingItem);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar a quantidade do item com ID " + id + ".", e);
        }
    }

    @Transactional(readOnly = true)
    public String getFoto(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
        return item.getFoto();
    }
}
