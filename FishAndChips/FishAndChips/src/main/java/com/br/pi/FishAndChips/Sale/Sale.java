package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Desk desk;

    private double price;

    @Column(name = "hour")
    private Date hoursInDesk;

    @OneToMany
    private List<SaleItem> saleItemList;

    @Enumerated(EnumType.STRING)
    private SaleState saleState;

}
