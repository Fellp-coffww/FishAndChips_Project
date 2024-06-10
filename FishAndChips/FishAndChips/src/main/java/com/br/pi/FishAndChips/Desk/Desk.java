package com.br.pi.FishAndChips.Desk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private DeskState tableState;

    private int occupantNumber;

    private Date arrivingHour;

    private Date exitHour;


}
