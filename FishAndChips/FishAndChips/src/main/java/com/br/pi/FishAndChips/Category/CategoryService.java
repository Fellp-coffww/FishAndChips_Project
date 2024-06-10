package com.br.pi.FishAndChips.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Category> findAllCategory(){

        return categoryRepository.findAll();
    }


    public void save(Category e){

        categoryRepository.save(e);

    }

    public Category findByName(String name){

        return categoryRepository.findByName(name);

    }

}
