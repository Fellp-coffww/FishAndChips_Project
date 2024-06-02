package com.br.pi.FishAndChips.Controller.Entity;

import com.br.pi.FishAndChips.Controller.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDB extends JpaRepository<Category, Integer> {
}
