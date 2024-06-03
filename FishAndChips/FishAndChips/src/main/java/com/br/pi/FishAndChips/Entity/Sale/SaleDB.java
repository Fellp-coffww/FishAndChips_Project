package com.br.pi.FishAndChips.Entity.Sale;

import com.br.pi.FishAndChips.Entity.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDB extends JpaRepository<Sale, Integer> {
}
