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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
