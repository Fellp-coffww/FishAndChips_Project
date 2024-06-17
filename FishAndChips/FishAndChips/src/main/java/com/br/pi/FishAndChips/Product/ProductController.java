package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.DashBoard.SaleReport;
import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Product.ProductDto;
import com.br.pi.FishAndChips.Product.ProductService;
import com.br.pi.FishAndChips.Sale.Sale;
import com.br.pi.FishAndChips.Sale.SaleController;
import com.br.pi.FishAndChips.SaleItem.SaleItemService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.PrimeFaces;
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
import java.util.Base64;
import java.util.List;

@ManagedBean
@Component
@SessionScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequestScoped
public class ProductController implements Serializable {

    private Product product = new Product();

    private Product selectedProduct;

    private List<Product> products;

    private List<Category> categoryList;

    private List<String> categoryNameList = new ArrayList<>();

    private Category category = new Category();


    private String categoryName;

    private UploadedFile originalImageFile;

    @Inject
    private ServletContext servletContext;

    @Inject
    private ProductService productService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private SaleItemService saleItemService;

    @Autowired
    ProductRepository productRepository;

    @Inject
    SaleController saleController;

    @Inject
    SaleReport saleReport;


    @PostConstruct
    public void init() {

        categoryList = categoryService.findAllCategory();
        products = productService.findAllTypeProducts();

        for (Category c : categoryList) {

            categoryNameList.add(c.getName());

        }

    }

    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException {
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);

        Product product = new Product();
        product.getClass();
        productService.create(product);
        return "redirect:/";
    }


    public void validateProduct(Product product) throws InvalidProductException {

        if (product.getName().length()<3 ){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Nome do produto não tem o tamanho mínimo de caracteres!"));
            throw new InvalidProductException();
        }
        originalImageFile = getOriginalImageFile();
        if ( originalImageFile == null){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Adicione uma imagem ao produto!"));
            throw new InvalidProductException();
        }

        if ( product.getPrice() == 0){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Adicione um preço ao produto!"));
            throw new InvalidProductException();
        }

    }

    public void newProduct(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("product.xhtml");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newCategory(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("category.xhtml");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public String saveProduct() throws Exception {
        try {
            validateProduct(product);
            product.setCategory(categoryService.findByName(categoryName));
            if (originalImageFile != null) {
                try (InputStream input = originalImageFile.getInputStream()) {
                    byte[] imageBytes = input.readAllBytes();
                    product.setImage(Base64.getEncoder().encodeToString(imageBytes));
                }

                Product productToBase = new Product(product.getName(), product.getPrice(), product.getDescription(), product.getImage(), product.getCategory());

                productService.create(productToBase);
            }

            products = productService.findAllTypeProducts();
            saleController.updateProduct();
            saleReport.init();
            FacesContext.getCurrentInstance().getExternalContext().redirect("productEdit.xhtml");
            return null;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    public void productById(String id){

        selectedProduct = productService.findById(Long.parseLong(id));

    }


    public void saveEdictedProduct(){

        try {
            productService.create(selectedProduct);
            products = productService.findAllTypeProducts();
            saleController.updateProduct();
            saleReport.init();
            PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void deleteProductById(String id){
        long idLong = Long.parseLong(id);
        saleItemService.deleteSaleItemByProductId(idLong);
        productService.deleteProductById(idLong);
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void deleteSelectedProduct(){
        saleItemService.deleteSaleItemByProductId(selectedProduct.getId());
        productService.deleteProduct(selectedProduct);
        products = productService.findAllTypeProducts();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }


}