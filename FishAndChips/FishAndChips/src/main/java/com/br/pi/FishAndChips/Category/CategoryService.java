package com.br.pi.FishAndChips.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public Category findByIdAndName(Long id, String name) {
        return categoryRepository.findByIdAndName(id, name);
    }

    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(ent -> new CategoryDto().fromEntity(ent)).collect(Collectors.toList());
    }

    public List<Category> findAllTyprCategory(){
        return categoryRepository.findAll();
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

    public long getLastID(){
       int size = categoryRepository.findAllByOrderById().size();
       return categoryRepository.findAllByOrderById().get(size-1).getId();

    }

}
