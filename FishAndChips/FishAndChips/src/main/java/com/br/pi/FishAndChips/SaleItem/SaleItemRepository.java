package com.br.pi.FishAndChips.SaleItem;


import com.br.pi.FishAndChips.Sale.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
}
