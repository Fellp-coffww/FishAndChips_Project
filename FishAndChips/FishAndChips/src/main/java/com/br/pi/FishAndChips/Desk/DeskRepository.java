package com.br.pi.FishAndChips.Desk;

import com.br.pi.FishAndChips.Category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {

    List<Desk> findAllByOrderById();


}
