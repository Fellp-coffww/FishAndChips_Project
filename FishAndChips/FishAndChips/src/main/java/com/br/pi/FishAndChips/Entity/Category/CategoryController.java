package com.br.pi.FishAndChips.Entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {

   Category categoryBean;

    @Autowired
    CategoryService categoryService;


    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> findAll(){
        try {
            List<CategoryDto> list = categoryService.findAll();
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("id") Integer id, @RequestParam(required = false) String name){

        if(name == null) {
            CategoryDto cat = new CategoryDto().fromEntity(categoryService.findById(id));
            return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);

        }
        else {
            CategoryDto cat = new CategoryDto().fromEntity(categoryService.findByIdAndName(id, name));
            return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);

        }
    }

    public void save(){

        categoryService.save(categoryBean);

    }




}
