package com.br.pi.FishAndChips.Product;

public class InvalidProductException extends Exception{

    private static final long serialVersionUID = 1149241039409861914L;

    public InvalidProductException(String msg){
        super(msg);
    }
    public InvalidProductException(){
        super.getMessage();
    }
}
