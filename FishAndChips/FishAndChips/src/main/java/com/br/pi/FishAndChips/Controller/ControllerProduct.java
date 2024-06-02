package com.br.pi.FishAndChips.Controller;


import com.br.pi.FishAndChips.Controller.Entity.ProductDB;
import com.br.pi.FishAndChips.Controller.Entity.Product;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("ControllerProduct")
@Component
@SessionScoped
public class ControllerProduct {

private Product product = new Product();

private List<Product> products;


@Autowired
private ProductDB productDB;

@PostConstruct
public void init (){

    products = productDB.findAll();

}


public void reset(){

    products = productDB.findAll();

}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void save(RowEditEvent<Product> event) {

        productDB.save(event.getObject());

    }

}
