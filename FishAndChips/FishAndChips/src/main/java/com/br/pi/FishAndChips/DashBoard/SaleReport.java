package com.br.pi.FishAndChips.DashBoard;


import com.br.pi.FishAndChips.Sale.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleReport {



    private List<Sale> saleFilteredByHour = new ArrayList<>();


    public void goToReport(){

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("relatorio.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
