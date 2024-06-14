package com.br.pi.FishAndChips.Desk;

import com.br.pi.FishAndChips.Category.CategoryDto;
import com.br.pi.FishAndChips.Category.CategoryService;
import com.br.pi.FishAndChips.Sale.SaleController;
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
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Controller
@RequestMapping("/api/desk")
@ManagedBean
@SessionScoped
public class DeskController implements Serializable {

    @Inject
    SaleController saleController;

    @Autowired
    DeskService deskService;

    @Autowired
    CategoryService categoryService;

    private String category;


    private List<Desk> desks = new ArrayList<>();

    @PostConstruct
    public void init(){

        updateLists();

    }

    public void updateLists(){

        desks = deskService.getAllDesksOrderedById();

        if(desks.size() == 0){
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            for (int i = 0; i < 40; i++) {
                desks.add(new Desk(i, DeskState.FREE,0, Date.from(zonedDateTime.toInstant()),Date.from(zonedDateTime.toInstant())));
                deskService.create(desks.get(i));
            }
        }

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

    public void navegarParaComanda(String deskId) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("Comanda.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        saleController.setDeskId(Long.parseLong(deskId));
        saleController.init();
    }
}
