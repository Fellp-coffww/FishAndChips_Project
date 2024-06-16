package com.br.pi.FishAndChips.Category;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@Getter
@Setter
@Controller
@RequestMapping("/api/category")
@ManagedBean
public class CategoryController {

    Category categoryBean;

    @Autowired
    CategoryService categoryService;

    String name = "erferferferferf";

    String description = "";


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


    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("id") Long id, @RequestParam(required = false) String name){

        if(name == null) {
            CategoryDto cat = new CategoryDto().fromEntity(categoryService.findById(id));
            return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);

        }
        else {
            CategoryDto cat = new CategoryDto().fromEntity(categoryService.findByIdAndName(id, name));
            return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);

        }
    }
    @GetMapping("/search")
    public String searchCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "searchCategory";
    }
    @GetMapping("/name")
    public String findByName(@RequestParam("name") String name, Model model) {
        CategoryDto category = new CategoryDto().fromEntity(categoryService.findByName(name));
        model.addAttribute("category", category);
        return "CategoryResult";
    }

    public void save() {

            categoryBean = new Category(categoryService.getLastID()+1,name, description);
            categoryService.save(categoryBean);

    }



}
