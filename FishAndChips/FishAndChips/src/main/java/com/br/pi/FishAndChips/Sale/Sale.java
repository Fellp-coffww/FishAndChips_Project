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

    @Column
    private double price;

    @Column(name = "Date")
    private Date date;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> saleItemList;

    @Enumerated(EnumType.STRING)
    private SaleState saleState;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;



    public Sale(Desk desk, double price, Date date, List<SaleItem> saleItemList, SaleState saleState) {
        this.desk = desk;
        this.price = price;
        this.date = date;
        this.saleItemList = saleItemList;
        this.saleState = saleState;
    }
}
