package com.br.pi.FishAndChips.Desk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeskService {

    @Autowired
    DeskRepository deskRepository;

    public List<DeskDto> findAll(){
        return deskRepository.findAll().stream().map(ent -> new DeskDto().fromEntity(ent)).collect(Collectors.toList());
    }

    public List<Desk> findAllTypeDesk(){
        return deskRepository.findAll();
    }


    public Desk findById(long id) {
        return deskRepository.findById(id).get();
    }

    public void create(Desk desk){
        deskRepository.save(desk);
    }

    public void update(Desk desk){

        deskRepository.save(desk);
    }

}

