package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

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

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
