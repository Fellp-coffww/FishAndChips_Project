package com.br.pi.FishAndChips.SaleItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleItemService {


@Autowired
SaleItemRepository saleItemRepository;

public void create(SaleItem saleItem){

    saleItemRepository.save(saleItem);

}


public List<SaleItem> findBySaleId(Long saleId){

    return saleItemRepository.findBySaleId(saleId);

}

}
