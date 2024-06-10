package com.br.pi.FishAndChips.Desk;

import com.br.pi.FishAndChips.Category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {



}
