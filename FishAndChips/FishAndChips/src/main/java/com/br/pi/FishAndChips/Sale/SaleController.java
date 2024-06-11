package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskState;
import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Product.ProductDto;
import com.br.pi.FishAndChips.Product.ProductService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.ResponsiveOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("SaleController")
@Controller
@SessionScoped
@Getter
@Setter
public class SaleController {

    @Autowired
    SaleService saleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    private String category;

    private Desk desk = new Desk(1, DeskState.FREE,9,new Date(),new Date());

    private List<CategoryDto> categories = new ArrayList<>();

    private List<ResponsiveOption> responsiveOptions;

    private List<SaleItem> saleItemList = new ArrayList<>();

    private List<SaleItem> activeSaleItemList = new ArrayList<>();

    private Sale sale = new Sale();

    private List<Product> products;

    private byte[] image;

    @PostConstruct
    public void init(){

        categories = categoryService.findAll();
        products = productService.findAllTypeProducts();
        long temp = 1;
        for(Product product:products){
            activeSaleItemList.add(new SaleItem(1,product.getPrice(),sale,product,temp));
            temp +=1;
        }

    }


    public void updateProductsByCategoryName(){

        products = productService.findByCategoryNameTypeProduct(category);


    }


    public void addProductToSaleItemList(String id){

        Long longId = Long.parseLong(id);
        for (Product product : products) {
            if (product.getId() == longId){
            System.out.println(product.getName());
            break;
            }
        }


    }

    public void addProduct(String id){

        for (SaleItem saleItem:activeSaleItemList) {
            if(Long.parseLong(id) == saleItem.getProduct().getId()){
                saleItemList.add(saleItem);
                saleItem.setQuantity(1);
                System.out.println(saleItem.getProduct().getName());
            }
        }


    }

    public void endCommand(){

        System.out.println("teteeteetet");
    }


}
