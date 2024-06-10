package com.br.pi.FishAndChips.Product;


import com.br.pi.FishAndChips.Category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.jfree.chart.ChartUtils;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("ControllerProduct")
@Component
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequestScoped
public class ProductController {

private Product product = new Product();

private List<ProductDto> products;

private List<byte[]> images = new ArrayList<>();

private byte[] image;

@Autowired
private ProductService productService;

private CroppedImage croppedImage;

private UploadedFile originalImageFile;


@PostConstruct
public void init (){

    products = productService.findAll();
    getDynamicImage();

    for (int i = 0; i< products.size(); i++){

        images.add(products.get(i).getImage());

    }
}

    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);

        Product product = new Product();
        product.getClass();
        productService.create(product);
        return "redirect:/";
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = null;
        this.croppedImage = null;
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            Product product1 = new Product();
            product1.setId(2);
            product1.setImage(file.getContent());
            productService.create(product1);
            this.originalImageFile = file;
            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void getDynamicImage() {
        products = productService.findAll();
        image = products.get(0).getImage(); // Método para obter os bytes da imagem
        //InputStream is = new ByteArrayInputStream(image);
        //return DefaultStreamedContent.builder()
          //      .stream(() -> is)
            //    .contentType("image/png")
              //  .build();
    }



}
