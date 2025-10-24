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
    @JoinColumn(name = "idItem")
    private Item item;

}
