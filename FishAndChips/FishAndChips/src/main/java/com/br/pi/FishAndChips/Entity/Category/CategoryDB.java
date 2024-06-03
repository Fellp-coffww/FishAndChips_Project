package com.br.pi.FishAndChips.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDB extends JpaRepository<Category, Integer> {
}
