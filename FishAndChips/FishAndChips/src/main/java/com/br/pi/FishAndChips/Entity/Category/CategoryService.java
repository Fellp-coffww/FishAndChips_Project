package com.br.pi.FishAndChips.Entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public Category findByIdAndName(Integer id, String name) {
        return categoryRepository.findByIdAndName(id, name);
    }

    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(ent -> new CategoryDto().fromEntity(ent)).collect(Collectors.toList());
    }

    public void save(Category e){

        categoryRepository.save(e);

    }

}
