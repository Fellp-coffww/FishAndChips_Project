package com.br.pi.FishAndChips.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@SessionScoped
public class SpringManagedBean {

    private String message = "OK ";

    public void acao() {
        message += " Clicou";
    }

    public String getMessage() {
        return message.toUpperCase();
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
