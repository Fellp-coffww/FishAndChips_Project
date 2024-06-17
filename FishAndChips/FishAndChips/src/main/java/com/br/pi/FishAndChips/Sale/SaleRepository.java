package com.br.pi.FishAndChips.Sale;

import com.br.pi.FishAndChips.Desk.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

List<Sale> findSaleByDesk(Desk desk);

List<Sale> findBySaleState(SaleState saleState);

Sale findSaleBySaleStateAndId(SaleState saleState, Long id);


Sale findSaleById(Long id);

List<Sale> findByDeskIdAndSaleState(Long deskId, SaleState saleState);

List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
