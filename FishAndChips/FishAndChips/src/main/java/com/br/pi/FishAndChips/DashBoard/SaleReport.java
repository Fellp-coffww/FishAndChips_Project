package com.br.pi.FishAndChips.DashBoard;


import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Sale.Sale;
import com.br.pi.FishAndChips.Sale.SaleService;
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

    private Product product;

    private LocalDate initialDate;

    private LocalDate finalDate;

    private List<String> reportTypes = new ArrayList<>();

    private String reportType;

    private LineChartModel salesValues;

    private BarChartModel deskMostValued;

    @Autowired
    SaleService saleService;

    @PostConstruct
    public void init(){

        reportTypes.add("Produtos");
        reportTypes.add("Intervalo de datas");
        reportTypes.add("Intervalo de datas e produtos");

    }

    public void goToReport(){

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("relatorio.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReport(){

        saleFilteredByHour = saleService.findSalesByDateRange(initialDate, finalDate);
        Map<LocalDate, List<Sale>> salesByDate = saleService.groupSalesByDate(saleFilteredByHour);
        saleFilteredByHour.clear();
        for (LocalDate date : salesByDate.keySet()) {
            List<Sale> salesOnDate = salesByDate.get(date);
            saleFilteredByHour.addAll(salesOnDate);
        }
        createLineModel();
        createBarModelByDesks();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("report.xhtml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){

        System.out.println(initialDate);
        System.out.println(finalDate);
        System.out.println(reportType);
    }



    public Map<LocalDate, List<Sale>> getSalesGroupedByDate(List<Sale> sales) {
        return saleService.groupSalesByDate(sales);
    }

    public void createLineModel() {
        salesValues = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        for (Sale sale:saleFilteredByHour) {
            values.add(sale.getPrice());
        }
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Valor das vendas");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);
        data.addChartDataSet(dataSet);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> labels = new ArrayList<>();
        for (Sale sale:saleFilteredByHour) {
            labels.add(sale.getDate().format(formatter));
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




}
