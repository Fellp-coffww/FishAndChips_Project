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


    public Desk findById(long id) {
        return deskRepository.findById(id).get();
    }

}

