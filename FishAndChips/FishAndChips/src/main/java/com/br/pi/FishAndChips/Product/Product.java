package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import javax.persistence.*;
import java.util.Base64;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "image",columnDefinition = "varchar" )
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, double price, String description, String image, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
