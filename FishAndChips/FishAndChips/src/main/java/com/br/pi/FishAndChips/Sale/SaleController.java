package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskService;
import com.br.pi.FishAndChips.Desk.DeskState;
import com.br.pi.FishAndChips.Product.*;
import com.br.pi.FishAndChips.SaleItem.SaleItem;
import com.br.pi.FishAndChips.SaleItem.SaleItemService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.ResponsiveOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


@ManagedBean
@Controller
@Getter
@Setter
@ViewScoped
@Named
public class SaleController implements Serializable {

    @Autowired
    SaleService saleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    DeskService deskService;

    @Autowired
    SaleItemService saleItemService;

    //Entidades temporárias

    private long deskId = 0;

    private String category;

    private Desk desk;

    private Sale sale;


   //Listas

    private List<Category> categories = new ArrayList<>();

    private List<ResponsiveOption> responsiveOptions;

    private List<SaleItem> saleItemList = new ArrayList<>();

    private List<SaleItem> activeSaleItemList = new ArrayList<>();

    private List<Product> products = new ArrayList<>();


    @PostConstruct
    public void init(){

        if (deskId != 0) {
            desk = deskService.findById(deskId);

            if(desk.getTableState() == DeskState.BUSY){

                sale = saleService.findSalesByDeskIdAndState(deskId,SaleState.CREATED);
                saleItemList = saleItemService.findBySaleId(sale.getId());

            }
            else {

                if(saleItemList!=null) {

                    saleItemList.clear();

                }}

        }

        if(categories.isEmpty()){

            categories = categoryService.findAllTyprCategory();
        }

        if (products.isEmpty()){

            products = productService.findAllTypeProducts();

        }

       if(activeSaleItemList.isEmpty() && deskId != 0){
        long temp = 0;
            for(Product product:products){
                activeSaleItemList.add(new SaleItem(1,product.getPrice(),sale,product,temp));
                temp +=1;
            }
       }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(@PathVariable("id") Long id){

            Sale sale = saleService.findSaleById(id);
            return new ResponseEntity<Sale>(sale, HttpStatus.OK);

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


    public void addProduct(String id){

        int temp = Integer.parseInt(id);
        boolean tempBool = false;

        for (int i = 0; i < saleItemList.size(); i++) {

            if (saleItemList.get(i).getProduct().equals(activeSaleItemList.get(temp).getProduct())){

                saleItemList.get(i).addQuantityToProduct(activeSaleItemList.get(temp).getQuantity());
                saleItemList.get(i).updatePrice(activeSaleItemList.get(temp).getProduct().getPrice());
                saleItemService.create(saleItemList.get(i));
                activeSaleItemList.get(temp).setQuantity(1);
                tempBool = true;
            }
        }

        if (!tempBool ){

            //saleItemList.add(activeSaleItemList.get(temp).clone());

            saleItemList.add(
                    new SaleItem(activeSaleItemList.get(temp).getQuantity(),activeSaleItemList.get(temp).getPrice(),
                            sale,activeSaleItemList.get(temp).getProduct()));

            saleItemList.get(saleItemList.size()-1).updatePrice(saleItemList.get(saleItemList.size()-1).getProduct().getPrice());
            saleItemService.create(saleItemList.get(saleItemList.size()-1));
            activeSaleItemList.get(temp).setQuantity(1);

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

    public double getPriceList(){
        double price = 0;

        for(SaleItem saleItem:saleItemList){
            price += saleItem.getPrice();
        }
        return price;
    }

    public void openSale(){

        if (desk.getOccupantNumber() == 0){

            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Coloque ao menos uma pessoa no número de ocupantes."));

        } else {
            desk.setTableState(DeskState.BUSY);
            deskService.update(desk);
            FacesMessage msg = new FacesMessage("Sucesso!", "Comanda aberta com sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Sale newsale = new Sale(desk,getPriceList(),desk.getArrivingHour(), saleItemList,SaleState.CREATED);
            saleService.create(newsale);
            sale = newsale;
        }
    }
}
