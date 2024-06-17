package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryRepository;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.bean.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ApplicationScoped
public class ProductService {


    @Autowired
    ProductRepository productRepository;


    public Product create(Product product){
        return productRepository.save(product);
    }

    public List<ProductDto> findAll(){
        return productRepository.findAll().stream().map(ent -> new ProductDto().fromEntity(ent)).collect(Collectors.toList());
    }


    public Product findById(long id) {
        return productRepository.findById(id).get();
    }


    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDto> findByCategoryName(String category){

        return productRepository.findByCategoryName(category).stream().map(ent -> new ProductDto().fromEntity(ent)).collect(Collectors.toList());

    }
    public List<Product> findByCategoryNameTypeProduct(String category){

        return productRepository.findByCategoryName(category);

    }

    public List<Product> findAllByCategory(Category category){
        return productRepository.findAllByCategory(category);
    }


    public List<Product> findAllTypeProducts(){return productRepository.findAll();}


    public List<Product> findProductsByCategoryName(String category) {

        return productRepository.findProductsByCategoryName(category);

    }

    public void deleteProductById(Long id){
        productRepository.delete(findById(id));
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }


    public Product findByName(String name){

        return productRepository.findByName(name);

    }

}
