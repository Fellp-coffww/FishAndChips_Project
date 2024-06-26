package com.br.pi.FishAndChips.DashBoard;


import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Product.ProductService;
import com.br.pi.FishAndChips.Sale.Sale;
import com.br.pi.FishAndChips.Sale.SaleService;
import com.br.pi.FishAndChips.SaleItem.SaleItem;
import com.br.pi.FishAndChips.SaleItem.SaleItemService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.component.barchart.BarChart;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ManagedBean
@SessionScoped
@Named
public class SaleReport implements Serializable {

    private List<Sale> saleFilteredByHour = new ArrayList<>();

    private List<SaleItem> saleItemsFiltered = new ArrayList<>();

    private Product product = new Product();

    private String productName;

    private List<Product> productList = new ArrayList<>();

    private List<String> productNameList = new ArrayList<>();


    private LocalDate initialDate;

    private LocalDate finalDate;

    private List<String> reportTypes = new ArrayList<>();

    private String reportType;

    private int permission;

    private LineChartModel salesValues;

    private BarChartModel deskMostValued;


    @Autowired
    SaleService saleService;

    @Autowired
    SaleItemService saleItemService;

    @Autowired
    ProductService productService;

    @PostConstruct
    public void init() {

        productList = productService.findAllTypeProducts();

        for (Product product: productList) {
            productNameList.add(product.getName());
        }

        if(reportType.isEmpty()) {
            reportTypes.add("Produtos");
            reportTypes.add("Mesas");
        }
    }

    public void goToReport() {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("relatorio.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateConsult(){

        if(initialDate.isBefore(finalDate) && productName != null){
            return true;
        }else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Selecione datas ou produtos válidos!"));
            return false;
        }

    }


    public void generateReport() {


        if(validateConsult()){
        saleFilteredByHour = saleService.findSalesByDateRange(initialDate, finalDate);
        Map<LocalDate, List<Sale>> salesByDate = saleService.groupSalesByDate(saleFilteredByHour);
        saleFilteredByHour.clear();
        for (LocalDate date : salesByDate.keySet()) {
            List<Sale> salesOnDate = salesByDate.get(date);
            saleFilteredByHour.addAll(salesOnDate);
        }

        if(permission == 1){
        createLineModel();
        createBarModelByPrduct();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("report.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        } else if (permission ==2 ) {
            createLineModel();
            createBarModelByDesks();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("report.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        }

    }

    public void update(){

        if(reportType.equals("Produtos")){
            permission = 1;
        } else if (reportType.equals("Mesas")) {
            permission = 2;
        }else{
            permission = 0;
        }

        System.out.println(initialDate);
        System.out.println(finalDate);
        System.out.println(reportType);
        System.out.println(permission);
        System.out.println(product);
    }

    public Map<LocalDate, List<Sale>> getSalesGroupedByDate(List<Sale> sales) {
        return saleService.groupSalesByDate(sales);
    }

    public void createLineModel() {
        salesValues = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();

        double temp = 0;
        for (int i = 0; i < saleFilteredByHour.size(); i++) {
            if(i+1 < saleFilteredByHour.size()){

                if(saleFilteredByHour.get(i).getDate().isAfter(saleFilteredByHour.get(i+1).getDate())){
                            temp += saleFilteredByHour.get(i).getPrice();
                            values.add(temp);
                            temp = 0;
                }else{
                    temp += saleFilteredByHour.get(i).getPrice();
                }

            }
        }
        values.add(temp);
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Valor das vendas");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);
        data.addChartDataSet(dataSet);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> labels = new ArrayList<>();

        for (Sale sale:saleFilteredByHour) {
            if (!labels.contains(sale.getDate().format(formatter))){
                 labels.add(sale.getDate().format(formatter));
            }
        }

        data.setLabels(labels);
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Vendas");
        options.setTitle(title);

        Title subtitle = new Title();
        subtitle.setDisplay(true);
        subtitle.setText("Vendas baseados em períodos");
        options.setSubtitle(subtitle);

        salesValues.setOptions(options);
        salesValues.setData(data);
    }


    public void createBarModelByDesks() {
        deskMostValued = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Vendas por mesas");

        List<Number> values = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        for (Sale sale:saleFilteredByHour) {
            if(!ids.contains(sale.getDesk().getId())){
                values.add(sale.getPrice());
                ids.add(sale.getDesk().getId());
            }
            else {
                double temp = values.get(ids.indexOf(sale.getDesk().getId())).doubleValue();
                values.remove(values.get(ids.indexOf(sale.getDesk().getId())));
                values.add(ids.indexOf(sale.getDesk().getId()), (temp + sale.getPrice()));
            }
        }

        barDataSet.setData(values);
        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for (Sale sale:saleFilteredByHour) {
            if(!labels.contains(String.valueOf(sale.getDesk().getId()))){
                    labels.add(String.valueOf(sale.getDesk().getId()));
            }
        }

        data.setLabels(labels);
        deskMostValued.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        options.setMaintainAspectRatio(false);
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Mesas mais vendidas");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        deskMostValued.setOptions(options);

    }

    public void createBarModelByPrduct() {


        product = productService.findByName(productName);
        deskMostValued = new BarChartModel();
        ChartData data = new ChartData();


        saleFilteredByHour = saleService.findSalesByDateRange(initialDate, finalDate);
        for(Sale sale:saleFilteredByHour) {
            saleItemsFiltered.addAll(saleItemService.findBySaleId(sale.getId()));
        }
        double temp = 0;
        for (SaleItem saleItem: saleItemsFiltered) {

            if(saleItem.getProduct().getId() == product.getId()){
                    temp += saleItem.getPrice();
            }

        }
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Vendas por produto " +product.getName());

        List<Number> values = new ArrayList<>();

        values.add(temp);
        barDataSet.setData(values);
        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        labels.add(product.getName());

        data.setLabels(labels);
        deskMostValued.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        options.setMaintainAspectRatio(false);
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Produto com vendas: ");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        deskMostValued.setOptions(options);


    }


}
