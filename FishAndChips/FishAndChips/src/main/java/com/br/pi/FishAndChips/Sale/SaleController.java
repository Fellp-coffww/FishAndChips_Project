package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskController;
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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Inject
    DeskController deskController;


    //Entidades temporárias

    private long deskId = 0;

    private String category;

    private Desk desk;

    private Sale sale;

    private double pricePeople;

    private boolean waiterTax;

    private String viewCommand;

    private PaymentMethod paymentMethod;


   //Listas

    private List<Category> categories = new ArrayList<>();

    private List<ResponsiveOption> responsiveOptions;

    private List<SaleItem> saleItemList = new ArrayList<>();

    private List<SaleItem> activeSaleItemList = new ArrayList<>();

    private List<Product> products = new ArrayList<>();

    private List<Sale> sales = new ArrayList<>();


    @PostConstruct
    public void init(){

        if (deskId != 0) {
            desk = deskService.findById(deskId);

            if(desk.getTableState() != DeskState.FREE){

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

        if (sales.isEmpty()){

            sales = saleService.findAll();

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

    public void updateProductsByCategoryName() {
        if (!category.equals("")) {
            activeSaleItemList.clear();
            products = productService.findProductsByCategoryName(category);
        } else {
            activeSaleItemList.clear();
            products = productService.findAllTypeProducts();
        }

        long temp = 0;
        for (Product product : products) {
            activeSaleItemList.add(new SaleItem(1, product.getPrice(), sale, product, temp));
            temp += 1;
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

        sale.setPrice(getPriceList());
        pricePerPeople();


        viewCommand = commandInfo();

        if (desk.getTableState() != DeskState.WAINTING_PAYMENT) {

            desk.setTableState(DeskState.WAINTING_PAYMENT);
            deskService.create(desk);
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("finalizarComanda.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finishHim(){

        sale.setSaleState(SaleState.FINISHED);
        desk.setTableState(DeskState.FREE);
        deskService.create(desk);
        saleService.create(sale);
        deskController.init();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public double getPriceList(){
        double price = 0;

        for(SaleItem saleItem:saleItemList){
            price += saleItem.getPrice();
        }

        if (waiterTax){
            price = price + (price*0.1);
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
            LocalDate localDate = LocalDate.now();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Date date = Date.from(zonedDateTime.toInstant());
            Sale newsale = new Sale(desk,getPriceList(),date, saleItemList,SaleState.CREATED);
            saleService.create(newsale);
            sale = newsale;
            deskController.updateLists();
        }
    }


    public String commandInfo() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String temp = "";

        int n = 1;

        temp = "JELF Dynamic's \n" +
                "RUA R. 227-A, 95 \n" +
                "Goiânia-GO \n" +
                "\n" +
                String.format("CNPJ: XX.XXX.XXX/XXX-XX..............%s\n", LocalDate.now().format(dateFormatter)) +
                "_________________________________________________________\n\n" +
                String.format("%-4s %-15s %-30s %-10s %10s\n", "ITEM", "COD.", "DESCRIÇÃO", "QTD.", "VALOR");

        for (SaleItem saleItem : saleItemList) {
            temp += String.format("%-4d %-15s %-30s %-10d %10.2f R$\n", n, saleItem.getId(), saleItem.getProduct().getName(), saleItem.getQuantity(), saleItem.getPrice());
            n++;
        }

        if(waiterTax){

            temp += String.format("\n\n Taxa de serviço cobrada em 10%% no valor de: %10.2f R$\n", (sale.getPrice() / 11));


        }

        temp += "\n" + "_________________________________________________________\n\n";  // Adiciona uma linha em branco antes do total
        temp += String.format("TOTAL: %10.2f R$", sale.getPrice());

        return temp;
    }

    public void pricePerPeople(){
        pricePeople = sale.getPrice()/ desk.getOccupantNumber();

    }



    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }


    public void cancellComand(){

        sale.setSaleState(SaleState.CANCELLED);
        saleService.create(sale);
        desk.setTableState(DeskState.FREE);
        deskService.create(desk);
        deskController.init();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void returnDesk(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
