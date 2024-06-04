package com.br.pi.FishAndChips.Entity.Product;


import com.br.pi.FishAndChips.Entity.Category.Category;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
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

    if (products.size() == 0){
        //productDB.save(new Product("Batata frita", "Porção: 200g", 12.00, new Category("comidas")));
        //productDB.save(new Product("Batata frita com cheddar e bacon", "Porção: 250g", 18.00, new Category("comidas")));
        //productDB.save(new Product("Bolinho de Bacalhau", "Porção: 6 unidades (aproximadamente 180g)", 24.00, new Category("comidas")));
        //productDB.save(new Product("Ceviche de Tilápia", "Porção: 150g (ceviche) + 50g (chips de batata-doce)", 28.00, new Category("comidas")));
        //productDB.save(new Product("Camarão ao Alho e Óleo", "Porção: 150g", 32.00, new Category("comidas")));
        //productDB.save(new Product("Croquete de costela", "Porção: 6 unidades (aproximadamente 180g)", 22.00, new Category("comidas")));
    }

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
