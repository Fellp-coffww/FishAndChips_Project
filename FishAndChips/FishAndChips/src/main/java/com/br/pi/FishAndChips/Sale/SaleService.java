package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

public List<Sale> findSalesByDateRange(LocalDate startDate, LocalDate endDate) {

        return saleRepository.findByDateBetween(startDate, endDate);

}

    public Map<LocalDate, List<Sale>> groupSalesByDate(List<Sale> sales) {
        // Map para armazenar as vendas agrupadas por data
        Map<LocalDate, List<Sale>> salesByDate = new HashMap<>();

        // Agrupando as vendas por data utilizando Java Streams
        salesByDate = sales.stream()
                .collect(Collectors.groupingBy(Sale::getDate, Collectors.toList()));

        return salesByDate;
    }

}
