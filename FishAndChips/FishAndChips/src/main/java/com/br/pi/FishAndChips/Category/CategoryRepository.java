package com.br.pi.FishAndChips.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByIdAndName(Long id, String name);

    Category findByName(String name);

}
