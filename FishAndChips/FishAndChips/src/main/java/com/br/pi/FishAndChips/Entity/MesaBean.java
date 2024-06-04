package com.br.pi.FishAndChips.Entity;

import javax.faces.bean.ViewScoped;

@javax.faces.bean.ManagedBean
@ViewScoped
public class MesaBean {

    private int totalMesas = 40;

    public int getTotalMesas() {
        return totalMesas;
    }

    public String redirectToComanda(int mesa) {
        return "comanda.xhtml?faces-redirect=true&mesa=" + mesa;
    }


}
