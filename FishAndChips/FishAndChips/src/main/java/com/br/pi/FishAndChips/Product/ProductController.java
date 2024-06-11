package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Product.ProductDto;
import com.br.pi.FishAndChips.Product.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("ProductController")
@Component
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequestScoped
public class ProductController implements Serializable {

private Product product = new Product();

private List<ProductDto> products;

private List<Category> categoryList;

private List<String> categoryNameList = new ArrayList<>();

private Category category = new Category();


private String categoryName;


@Inject
private ServletContext servletContext;

@Inject
private ProductService productService;

@Inject
private CategoryService categoryService;



private UploadedFile originalImageFile;


@PostConstruct
public void init (){

    categoryList = categoryService.findAllCategory();

    for (Category c: categoryList){

        categoryNameList.add(c.getName());

    }


}
    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);

        Product product = new Product();
        product.getClass();
        productService  .create(product);
        return "redirect:/";
    }

    public String salvarProduto() throws IOException {


        product.setCategory(categoryService.findByName(categoryName));
        if (originalImageFile != null) {
            try (InputStream input = originalImageFile.getInputStream()) {
                byte[] imageBytes = input.readAllBytes();
                product.setImage(imageBytes);
            }
            productService.create(product);
        }
        FacesMessage msg = new FacesMessage("Produto salvo!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

}
    