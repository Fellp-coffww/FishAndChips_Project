package com.br.pi.FishAndChips.Entity.Sale;

import com.br.pi.FishAndChips.Entity.Product.Product;

import javax.persistence.*;
import java.util.List;


@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double price;


    @OneToMany
    private List<SaleItem> saleItemList;


}
