package com.stock.manager.StockManager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    private String tipoTransacao;
    private Date data;
    private Double valor;

    @ManyToOne
    @JoinColumn(
            name = "id_Item",
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (id_Item) REFERENCES " +
                            "item(id) ON DELETE SET NULL"
            )
    )
    private Item item;

}
