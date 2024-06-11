package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {

    @Column
    private int quantity = 0;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public void addQuantityToProduct(){

        quantity +=1;

    }

    public void decreaseQuantityToProduct(){

        if (quantity >1) {
            quantity -= 1;
        }
        else {
            quantity = 1;
        }
    }

}