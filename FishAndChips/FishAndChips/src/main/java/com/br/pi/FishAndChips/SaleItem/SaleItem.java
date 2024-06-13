package com.br.pi.FishAndChips.SaleItem;

import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Sale.Sale;
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


    public void addQuantityToProduct(int increseValue){
        this.quantity = increseValue + this.quantity ;

    }
    public void updatePrice(double price){

        price = price * this.quantity;
        this.price = price;


    }

    public void increaseQuantityToProduct(){
        this.quantity +=1;
    }


    public void decreaseQuantityToProduct(){

        if (this.quantity > 1) {
            this.quantity -= 1;
        }
        else {
            this.quantity = 1;
        }
    }

    public SaleItem clone(){

        return new SaleItem(this.quantity, this.price, this.sale, this.product,this.id);

    }

    public SaleItem(int quantity, double price, Sale sale, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.sale = sale;
        this.product = product;
    }
}