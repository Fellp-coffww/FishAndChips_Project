package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskState;
import com.br.pi.FishAndChips.Product.*;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.ResponsiveOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ManagedBean
@Controller
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

    private List<Category> categories = new ArrayList<>();

    private List<ResponsiveOption> responsiveOptions;

    private List<SaleItem> saleItemList = new ArrayList<>();

    private List<SaleItem> activeSaleItemList = new ArrayList<>();

    private Sale sale = new Sale();

    private List<Product> products;

    private byte[] image;

    private List<Image> imagesList = new ArrayList<>();

    @PostConstruct
    public void init(){


        categories = categoryService.findAllTyprCategory();
        products = productService.findAllTypeProducts();
        long temp = 0;

        for(Product product:products){
            activeSaleItemList.add(new SaleItem(1,product.getPrice(),sale,product,temp));
            temp +=1;
        }



    }
   public void updateProductsByCategoryName(){

    long temp = 0;
        if(!category.equals("Seleciona a categoria do produto: ")) {

            Category category1 = new Category();
            category1 = categoryService.findByName(category);
            activeSaleItemList.clear();
            products = productService.findProductsByCategoryName(category);
            for(Product product:products){
                activeSaleItemList.add(new SaleItem(1,product.getPrice(),sale,product, temp));
                temp +=1;
            }
        }

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

        int temp = Integer.parseInt(id);
        boolean tempBool = false;

        for (SaleItem saleItem: saleItemList) {

            if (saleItem.getProduct().equals(activeSaleItemList.get(temp).getProduct())){

                saleItem.addQuantityToProduct(activeSaleItemList.get(temp).getQuantity());
                saleItem.updatePrice(activeSaleItemList.get(temp).getProduct().getPrice());
                tempBool = true;
            }

        }

        if (!tempBool ){

            saleItemList.add(activeSaleItemList.get(temp).clone());
        }
    }


    public void decreaseProduct(String id){

        Long temp = Long.parseLong(id);

        for (SaleItem saleItem: saleItemList) {

                if(saleItem.getId() == temp) {
                    if(saleItem.getQuantity()  <= 1){
                        saleItem.decreaseQuantityToProduct();
                        saleItemList.remove(saleItem);
                        break;
                    }
                    else {
                        saleItem.decreaseQuantityToProduct();
                        saleItem.updatePrice(saleItem.getProduct().getPrice());
                    }
                }
        }
        PrimeFaces.current().ajax().update("form2:comandaa");
    }


    public void endCommand(){

        System.out.println("teteeteetet");
    }



}
