package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryRepository;
import com.br.pi.FishAndChips.Desk.Desk;
import com.br.pi.FishAndChips.Desk.DeskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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


    public List<ProductDto> findByCategory(Category category){
       return productRepository.findByCategory(category).stream().map(ent -> new ProductDto().fromEntity(ent)).collect(Collectors.toList());
    }

}
