package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Controller
@ManagedBean("SaleController")
public class SaleController {

    @Autowired
    SaleService saleService;

    @Autowired
    CategoryService categoryService;

    private String category;

    private List<CategoryDto> categories = new ArrayList<>();

    @PostConstruct
    public void init(){

        categories = categoryService.findAll();

    }



}
