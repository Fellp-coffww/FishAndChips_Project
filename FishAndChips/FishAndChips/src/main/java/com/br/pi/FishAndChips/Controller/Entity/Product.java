package com.br.pi.FishAndChips.Controller.Entity;

import javax.persistence.*;

@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String description;


    private String categoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {

        return categoryName;
    }

    public void setCategory(String category) {
        this.categoryName = category;
    }

    public Product(String name, String description, double price, String categoryName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    }

    public Product() {

    }

}
