package com.br.pi.FishAndChips.Entity;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@ManagedBean("MesaBean")
@ViewScoped
public class MesaBean implements Serializable {
        private List<String> mesas;

    @PostConstruct
    public void init() {
        mesas = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            mesas.add("Mesa " + i);
        }
    }

    public List<String> getMesas() {
        return mesas;
    }
}