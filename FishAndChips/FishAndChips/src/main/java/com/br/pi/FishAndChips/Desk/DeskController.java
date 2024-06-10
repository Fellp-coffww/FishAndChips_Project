package com.br.pi.FishAndChips.Desk;


import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Controller
@RequestMapping("/api/desk")
@ManagedBean("DeskController")
@SessionScoped
@Component
public class DeskController {

    @Autowired
    DeskService deskService;

    @Autowired
    CategoryService categoryService;

    private String category;

    private List<CategoryDto> categories = new ArrayList<>();

    private int occupantNumber = 0;

    @PostConstruct
    public void init(){

        categories = categoryService.findAll();

    }


    @GetMapping("/")
    public ResponseEntity<List<DeskDto>> findAll(){
        try {
            List<DeskDto> list = deskService.findAll();
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<DeskDto> findById(@PathVariable("id") Long id){

            DeskDto cat = new DeskDto().fromEntity(deskService.findById(id));
            return new ResponseEntity<DeskDto>(cat, HttpStatus.OK);


    }

    public void updateCategorys(){this.categories = categoryService.findAll();}


}
