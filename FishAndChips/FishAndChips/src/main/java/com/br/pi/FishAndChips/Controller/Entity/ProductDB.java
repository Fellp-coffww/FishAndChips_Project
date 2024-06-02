package com.br.pi.FishAndChips.Controller.Entity;

import com.br.pi.FishAndChips.Controller.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDB extends JpaRepository<Product, Integer> {


}
