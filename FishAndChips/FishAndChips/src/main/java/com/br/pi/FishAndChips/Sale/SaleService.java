package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleService {


@Autowired
public SaleRepository saleRepository;



public List<Sale>findSaleByDesk(Desk desk){

return saleRepository.findSaleByDesk(desk);

}

public List<Sale> findAll(){

    return saleRepository.findAll();
}

public List<Sale> findSaleCreated(){

   return saleRepository.findBySaleState(SaleState.CREATED);
}

public Sale findSaleByDeskIdAndSaleStateCreated(Long id){
    return saleRepository.findSaleBySaleStateAndId( SaleState.CREATED, id);
}

public Sale findSaleById(Long id){

    return saleRepository.findSaleById(id);
}

public void create(Sale sale){

    saleRepository.save(sale);
}
public Sale findSalesByDeskIdAndState(Long deskId, SaleState saleState) {

        return saleRepository.findByDeskIdAndSaleState(deskId, saleState).get(0);

}

}
