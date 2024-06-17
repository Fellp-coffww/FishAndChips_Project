package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

 List<Product> findByCategory(Category category);

 List<Product> findByCategoryName(String name);

List<Product> findProductByCategory(Category category);

List<Product> findAllByCategory(Category category);


List<Product> findProductsByCategoryName(String categoryName);

 Product findByName(String name);

}
