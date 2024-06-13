package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.SaleItem.SaleItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> saleItemList;

    @Enumerated(EnumType.STRING)
    private SaleState saleState;


    public Sale(Desk desk, double price, Date hoursInDesk, List<SaleItem> saleItemList, SaleState saleState) {
        this.desk = desk;
        this.price = price;
        this.hoursInDesk = hoursInDesk;
        this.saleItemList = saleItemList;
        this.saleState = saleState;
    }
}
