package com.br.pi.FishAndChips.Sale;

public enum SaleState {

    CREATED("created"),
    FINISHED("finished"),
    CANCELLED("cancelled");

    String value;

    SaleState(String value){
        this.value = value;
    }


}
