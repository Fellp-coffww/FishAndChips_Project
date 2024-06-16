package com.br.pi.FishAndChips.Category;

import com.br.pi.FishAndChips.Desk.Desk;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByIdAndName(Long id, String name);

    Category findByName(String name);

    List<Category> findAllByOrderById();


}
