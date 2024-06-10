package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SaleService {

public Desk desk;

public List<Sale> sales = new ArrayList<Sale>();

@Autowired
public SaleRepository saleRepository;


public List<Sale>findSaleByDesk(Desk desk){

return saleRepository.findSaleByDesk(desk);

}

public List<Sale> findSaleCreated(){

   return saleRepository.findBySaleState(SaleState.CREATED);
}

public Sale findSaleByDeskIdAndSaleStateCreated(Long id){
    return saleRepository.findSaleBySaleStateAndId( SaleState.CREATED, id);
}

}
